package com.gen.poc.loanapproval.camunda.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoJobWorker {

    @JobWorker(type = "demoServiceTask", autoComplete = true)
    public void demoWorker(){
      log.info("test job worker");
    }
}
