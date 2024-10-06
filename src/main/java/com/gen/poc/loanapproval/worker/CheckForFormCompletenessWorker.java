package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckForFormCompletenessWorker {

    @JobWorker(type = "checkForFormCompletenessServiceTask", autoComplete = true)
    public void checkForFormCompletenessServiceTask(){
//      System.console().printf("Test checkForFormCompletenessServiceTask");
        System.out.println("Test checkForFormCompletenessServiceTask console");
        log.info("test checkForFormCompletenessServiceTask worker");
    }
}
