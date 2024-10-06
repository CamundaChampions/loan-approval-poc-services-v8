package com.gen.poc.loanapproval.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class NotifyToApplicantWorker {

    @JobWorker(type = "notifyToApplicantServiceTask", autoComplete = true)
    public Map<String, Object> notifyToApplicantServiceTask(final JobClient client, final ActivatedJob job,
                                             @Variable("taskType") String taskType){
        Map<String, Object> returnType = new HashMap<>();
        if("docSigning".equals(taskType)){
            returnType.put("MSGEVNT_SIGNED_DOC_RECIEVED", "MSGEVNT_SIGNED_DOC_RECIEVED-"+job.getProcessInstanceKey());
        }
      log.info("test notifyToApplicantServiceTask worker");
        return returnType;
    }
}
