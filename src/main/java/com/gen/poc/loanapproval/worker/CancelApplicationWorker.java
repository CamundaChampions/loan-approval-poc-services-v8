package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelApplicationWorker {


    @JobWorker(type = "cancelApplicationServiceTask", autoComplete = true)
    public void cancelApplicationServiceTask(){
      log.info("test cancelApplicationServiceTask worker");
    }
}
