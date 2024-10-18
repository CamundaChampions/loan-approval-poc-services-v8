package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DisbursementInstructionWorker {

    private final LoanApplicationRepository loanApplicationRepository;

    @JobWorker(type = "disbursementInstructionServiceTask", autoComplete = true)
    public void disbursementInstructionServiceTask(final JobClient client, final ActivatedJob job){

        log.info("test disbursementInstructionServiceTask worker");

        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(LoanApplicationStatus.APPROVE_AND_DISBURSED);
        loanApplicationRepository.save(loanApplication.get());
    }
}
