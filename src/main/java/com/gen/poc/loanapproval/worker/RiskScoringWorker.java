package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RiskScoringWorker {

    @JobWorker(type = "riskScoringServiceTask", autoComplete = true)
    public void riskScoringServiceTask(){
      log.info("test riskScoringServiceTask worker");
    }
}
