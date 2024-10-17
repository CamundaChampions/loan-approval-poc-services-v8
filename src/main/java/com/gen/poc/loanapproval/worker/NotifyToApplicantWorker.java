package com.gen.poc.loanapproval.worker;

import com.gen.poc.loanapproval.constants.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotifyToApplicantWorker {

    private final LoanApplicationRepository loanApplicationRepository;

    @JobWorker(type = "notifyToApplicantServiceTask", autoComplete = true)
    public Map<String, Object> notifyToApplicantServiceTask(final JobClient client, final ActivatedJob job,
                                                            @Variable("taskType") String taskType) {
        Map<String, Object> variables = job.getVariablesAsMap();
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        boolean isApplicationComplete = Boolean.parseBoolean(job.getVariablesAsMap().get("isApplicationComplete").toString());
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        loanApplication.ifPresent(loadApp -> {

            if (LoanApplicationStatus.CREATED.equals(loadApp.getStatus()) && !isApplicationComplete) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
                variables.put("missingAppDataReceivedAcknowledgement", String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, job.getProcessInstanceKey()));
            } else if ("docSigning".equals(taskType)) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
                variables.put("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, job.getProcessInstanceKey()));
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loadApp.getStatus())
                    && Boolean.parseBoolean(job.getVariablesAsMap().get("hasMissingData").toString())) {
                loadApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
                variables.put("missingDocProvidedAcknowledgement", String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, job.getProcessInstanceKey()));
            }
            loanApplicationRepository.save(loadApp);
            log.info("test notifyToApplicantServiceTask worker");
        });
        return variables;
    }

    @JobWorker(type = "notifyForDocumentSigning", autoComplete = true)
    public Map<String, Object> notifyForDocumentSigning(final JobClient client, final ActivatedJob job) {
        Map<String, Object> returnType = new HashMap<>();
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        LoanApplication loanApplication = findLoanApplicationById(loanApplicationId);

        loanApplication.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
        loanApplicationRepository.save(loanApplication);
        returnType.put("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, job.getProcessInstanceKey()));

        log.info("test notifyForDocumentSigning worker");
        return returnType;
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new RuntimeException("Invalid Loan Id");

        return loanApplication.get();

    }
}
