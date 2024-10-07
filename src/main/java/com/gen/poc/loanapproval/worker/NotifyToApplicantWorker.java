package com.gen.poc.loanapproval.worker;

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
        Map<String, Object> returnType = new HashMap<>();
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if ("docSigning".equals(taskType)) {
            loanApplication.get().setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
            loanApplicationRepository.save(loanApplication.get());
            returnType.put("MSGEVNT_SIGNED_DOC_RECIEVED", "MSGEVNT_SIGNED_DOC_RECIEVED-" + job.getProcessInstanceKey());
        }
        log.info("test notifyToApplicantServiceTask worker");
        return returnType;
    }
}
