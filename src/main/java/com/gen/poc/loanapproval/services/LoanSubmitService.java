package com.gen.poc.loanapproval.services;

import com.gen.poc.loanapproval.constants.AppConstants;
import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.enums.UserRoleAndUserList;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.LoanSummaryRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import com.gen.poc.loanapproval.repository.entity.LoanSummary;
import com.gen.poc.loanapproval.services.mapper.LoanRequestMapper;
import com.gen.poc.loanapproval.web.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.web.dto.LoanSummaryDto;
import com.gen.poc.loanapproval.web.dto.LoanSummaryListResponse;
import com.gen.poc.loanapproval.web.dto.LoanSummaryResponse;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanSubmitService {

    private final ZeebeClient zeebeClient;

    private final LoanRequestMapper loanRequestMapper;

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    private final LoanSummaryRepository loanSummaryRepository;


    public String processLoanRequest(String userId, LoanRequestDTO request) {

        LoanApplication loanApplication = loanRequestMapper.toLoanRequestEntityOnCreate(request);
        loanApplication.setStatus(LoanApplicationStatus.CREATED);
        loanApplication.setCustomerId(userId);
        loanApplication = loanApplicationRepository.save(loanApplication);
        Map<String, Object> params = new HashMap<>();

        params.put("loan-id", loanApplication.getLoanApplicationId());

        final ProcessInstanceEvent processInstanceEvent = zeebeClient
                .newCreateInstanceCommand().bpmnProcessId("LOAN_APPROVAL_PROCESS")
                .latestVersion().variables(params)
                .send().join();

        loanApplication.setProcessInstanceId(String.valueOf(processInstanceEvent.getProcessInstanceKey()));
        loanApplicationRepository.save(loanApplication);
        return String.valueOf(loanApplication.getLoanApplicationId());
    }

    public void completeUserTask(String loanApplicationId, ApprovalCategory taskName, Map<String, Object> additionalParam) {

        List<LoanApprovalTask> loanApprovalTask = loanApprovalTaskRepository.findByLoanApplicationIdAndTaskCategory(Long.valueOf(loanApplicationId), taskName);

        TaskStatus status = TaskStatus.COMPLETED;
        if (!CollectionUtils.isEmpty(loanApprovalTask) && loanApprovalTask.get(0).getStatus() == TaskStatus.IN_PROGRESS) {
            if ((boolean) additionalParam.get("hasMissingData")) {
                status = TaskStatus.REJECTED;
            }

            loanApprovalTask.get(0).setStatus(status);
            loanApprovalTaskRepository.save(loanApprovalTask.get(0));

            zeebeClient.newCompleteCommand(Long.parseLong(loanApprovalTask.get(0).getTaskInstanceId()))
                    .variables(additionalParam)
                    .send().join();
        }

    }

    public void acknowledgeDocumentSigning(String loanId, Map<String, Object> additionalParam) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));
        zeebeClient.newPublishMessageCommand()
                .messageName(AppConstants.MSGEVNT_SIGNED_DOC_RECEIVED)
                .correlationKey(String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, loanApplication.getProcessInstanceId()))
                .variables(additionalParam)
                .send().join();
    }

    public void acknowledgeDocumentReAssessment(String loanId, Map<String, Object> additionalParam) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));
        zeebeClient.newPublishMessageCommand()
                .messageName(AppConstants.MSGEVNT_AKNOWLEDGE_MISSING_DOC_PROVIDED)
                .correlationKey(String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, loanApplication.getProcessInstanceId()))
                .variables(additionalParam)
                .send().join();
    }

    public void updateDocumentDetails(String loanId, Map<String, Object> additionalParam, Optional<Long> amountOpt,
                                      Optional<Integer> termOpt) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));

        amountOpt.ifPresent(amount -> {
            if (amount > 0) {
                loanApplication.setAmount(BigDecimal.valueOf(amount));
            }
        });
        termOpt.ifPresent(term -> {
            if (term > 0) {
                loanApplication.setTerm(term);
            }
        });
        loanApplicationRepository.flush();

        // validate and verify if application is complete now
        if (ObjectUtils.allNotNull(loanApplication.getAmount(), loanApplication.getLoanCategory(), loanApplication.getTerm())
                && loanApplication.getAmount().longValue() > 0 && loanApplication.getTerm() > 0) {
            additionalParam.put("isApplicationComplete", true);
            zeebeClient.newPublishMessageCommand()
                    .messageName(AppConstants.MSGEVNT_MISSING_APP_DATA_RECIEVED_AKNWLG)
                    .correlationKey(String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, loanApplication.getProcessInstanceId()))
                    .variables(additionalParam)
                    .send().join();
        } else {
            log.info("Mandatory data is invalid or not provided cannot proceed");
        }
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new RuntimeException("Invalid Loan Id");

        return loanApplication.get();

    }

    public LoanSummaryListResponse findAllUserItems(String user) {
        LoanSummaryListResponse response = new LoanSummaryListResponse();
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(user);
        List<LoanSummaryDto> loanSummaries = findAllTaskByUser(user, userRole);
        response.setLoanSummaryList(loanSummaries);
        response.setAllowToCreateLoan(userRole == UserRoleAndUserList.APPLICANT && CollectionUtils.isEmpty(loanSummaries));
        return response;

    }

    public List<LoanSummaryDto> findAllTaskByUser(String user, UserRoleAndUserList userRole) {
        List<LoanSummary> loanSummaries = new ArrayList<>();
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            loanSummaries = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategory(ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.name());
        else if (userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER) {
            loanSummaries = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategory(ApprovalCategory.RISK_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL.name());
        } else if (userRole == UserRoleAndUserList.APPLICANT) {
            loanSummaries = loanSummaryRepository.getInProcessLoanApplicationItemsOfApplicant(user);
        }

        return loanRequestMapper.mapTo(loanSummaries);

    }

    public LoanSummaryResponse findLoanDetailsByIdAndUser(Long loanId, String userId){
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        LoanSummary loanSummary = new LoanSummary();
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            loanSummary = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategoryAndLoanId(ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.name(), loanId);
        else if (userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER) {
            loanSummary = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategoryAndLoanId(ApprovalCategory.RISK_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL.name(), loanId);
        } else if (userRole == UserRoleAndUserList.APPLICANT) {
            loanSummary = loanSummaryRepository.getInProcessLoanApplicationItemsOfApplicantAndLoanId(userId, loanId);
        }
        LoanSummaryResponse response = loanRequestMapper.mapToResponse(loanSummary);

        return response;
    }

}
