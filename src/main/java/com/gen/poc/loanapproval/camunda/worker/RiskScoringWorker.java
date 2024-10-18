package com.gen.poc.loanapproval.camunda.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RiskScoringWorker {

    @JobWorker(type = "riskScoringServiceTask", autoComplete = true)
    public Map<String, Object> riskScoringServiceTask(@Variable("creditScore") int creditScore){

        log.info("test riskScoringServiceTask worker");

        return Map.of("highRisk", creditScore < 800);
    }
}
