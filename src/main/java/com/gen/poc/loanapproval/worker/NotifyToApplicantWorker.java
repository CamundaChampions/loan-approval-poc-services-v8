package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotifyToApplicantWorker {

    @JobWorker(type = "notifyToApplicantServiceTask", autoComplete = true)
    public void notifyToApplicantServiceTask(){
      log.info("test notifyToApplicantServiceTask worker");
    }
}
