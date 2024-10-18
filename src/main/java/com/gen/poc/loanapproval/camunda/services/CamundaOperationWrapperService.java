package com.gen.poc.loanapproval.camunda.services;

import com.gen.poc.loanapproval.constants.AppConstants;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaOperationWrapperService {

    private final ZeebeClient zeebeClient;

    public ProcessInstanceEvent createCamundaProcessInstance(String processDefinitionKey, Map<String, Object> variables){
        return zeebeClient
                .newCreateInstanceCommand().bpmnProcessId(processDefinitionKey)
                .latestVersion().variables(variables)
                .send().join();
    }


    public void triggerCorrelateMessage(String messageKey, String correlationKey,  Map<String, Object> variables){
        zeebeClient.newPublishMessageCommand()
                .messageName(messageKey)
                .correlationKey(correlationKey)
                .variables(variables)
                .send().join();
    }

    public void completeUserTask(String userTaskId, Map<String, Object> variables){
        zeebeClient.newCompleteCommand(Long.parseLong(userTaskId))
                .variables(variables)
                .send().join();
    }


}
