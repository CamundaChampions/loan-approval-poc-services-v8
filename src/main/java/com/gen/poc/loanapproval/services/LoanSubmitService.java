package com.gen.poc.loanapproval.services;

import com.gen.poc.loanapproval.constant.enums.TaskStatus;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import com.gen.poc.loanapproval.services.mapper.LoanRequestMapper;
import com.gen.poc.loanapproval.web.dto.LoanRequestDTO;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanSubmitService {

    private final ZeebeClient zeebeClient;

    private final LoanRequestMapper loanRequestMapper;

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;


    public String processLoanRequest(String userId, LoanRequestDTO request){

        LoanApplication loanApplication = loanRequestMapper.toLoanRequestEntityOnCreate(request);
        loanApplication.setStatus(LoanApplicationStatus.CREATED);
        loanApplication.setCustomerId(userId);
        loanApplication = loanApplicationRepository.save(loanApplication);
        Map<String,Object> params = new HashMap<>();
        params.put("isApplicationComplete", true);
        params.put("loan-id", loanApplication.getLoanApplicationId());

        final ProcessInstanceEvent processInstanceEvent = zeebeClient
                .newCreateInstanceCommand().bpmnProcessId("LOAN_APPROVAL_PROCESS")
                .latestVersion().variables(params)
                .send().join();

        loanApplication.setProcessInstanceId(String.valueOf(processInstanceEvent.getProcessInstanceKey()));
        loanApplicationRepository.save(loanApplication);
        return String.valueOf(loanApplication.getLoanApplicationId());
    }

    public void completeUserTask(String loanApplicationId, String taskName, Map<String, Object> additionalParam){

        List<LoanApprovalTask> loanApprovalTask = loanApprovalTaskRepository.findByLoanApplicationIdAndTaskCategory(Long.valueOf(loanApplicationId), taskName);

        TaskStatus status = TaskStatus.COMPLETED;
        if(!CollectionUtils.isEmpty(loanApprovalTask) && loanApprovalTask.get(0).getStatus() == TaskStatus.IN_PROGRESS) {
            if((boolean)additionalParam.get("hasMissingData"))
                status = TaskStatus.REJECTED;

            loanApprovalTask.get(0).setStatus(status);
            loanApprovalTaskRepository.save(loanApprovalTask.get(0));

            zeebeClient.newCompleteCommand(Long.parseLong(loanApprovalTask.get(0).getTaskInstanceId()))
                    .variables(additionalParam)
                    .send().join();
        }

    }

    public void acknowledgeDocumentSigning(String loanId, Map<String, Object> additionalParam){

        zeebeClient.newPublishMessageCommand()
                .messageName("MSGEVNT_SIGNED_DOC_RECIEVED")
                .correlationKey("MSGEVNT_SIGNED_DOC_RECIEVED")
                .variables(additionalParam).send().join();
    }

}
