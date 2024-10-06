package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class UserTaskZeebeWorker {

    @JobWorker(type = "io.camunda.zeebe:userTask", autoComplete = false)
    public void handleJob(final JobClient client, final ActivatedJob job) {
        // Element Id
        log.info("user task task element id {}", job.getKey());
        log.info("task element id {}", job);

        // get variables

    }
}
