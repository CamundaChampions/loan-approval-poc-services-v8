package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class ThrowErrorWorker {

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(ThrowErrorWorker.class);

    @JobWorker(type = "throwErrorWorker", autoComplete = true)
    public void throwErrorWorker(){
      log.info("test riskScoringServiceTask worker");
    }
}
