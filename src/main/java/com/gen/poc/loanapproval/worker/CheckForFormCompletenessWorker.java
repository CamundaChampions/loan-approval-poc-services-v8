package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckForFormCompletenessWorker {

    @JobWorker(type = "checkForFormCompletenessServiceTask", autoComplete = true)
    public void checkForFormCompletenessServiceTask(final JobClient client, final ActivatedJob job){

        log.info("test checkForFormCompletenessServiceTask worker");
    }
}
