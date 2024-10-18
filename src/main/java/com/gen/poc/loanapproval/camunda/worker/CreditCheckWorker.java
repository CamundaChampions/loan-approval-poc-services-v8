package com.gen.poc.loanapproval.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class CreditCheckWorker {

    @JobWorker(type = "creditCheckServiceTask", autoComplete = true)
    public Map<String, Object> checkForFormCompletenessServiceTask(final JobClient client, final ActivatedJob job){
        log.info("test creditCheckServiceTask worker");
        String userId = job.getVariable("userId").toString();
        return Map.of("creditScore","applicant_1".equals(userId) ? 820: 700);
    }
}
