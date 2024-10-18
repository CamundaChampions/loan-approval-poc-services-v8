package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckForFormCompletenessWorker {

    private final LoanApplicationRepository loanApplicationRepository;

    @JobWorker(type = "checkForFormCompletenessServiceTask", autoComplete = true)
    public Map<String, Object> checkForFormCompletenessServiceTask(final JobClient client, final ActivatedJob job) {

        log.info("test checkForFormCompletenessServiceTask worker");

        Map<String, Object> variables = new HashMap<>();

        variables.put("isApplicationComplete", false);
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        // validate if all the data is provided correctly

        loanApplication.ifPresent(loanApp -> {
            if (ObjectUtils.allNotNull(loanApp.getAmount(), loanApp.getLoanCategory(), loanApp.getTerm())
                    && loanApp.getAmount().longValue() > 0 && loanApp.getTerm() > 0) {
                variables.put("isApplicationComplete", true);
            } else {
                log.info("Mandatory data is invalid or not provided");
            }
        });

        return variables;
    }
}
