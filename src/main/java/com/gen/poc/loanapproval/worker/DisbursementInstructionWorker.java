package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DisbursementInstructionWorker {


    @JobWorker(type = "disbursementInstructionServiceTask", autoComplete = true)
    public void disbursementInstructionServiceTask(){
      log.info("test disbursementInstructionServiceTask worker");
    }
}
