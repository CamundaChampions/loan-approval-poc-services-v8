package com.gen.poc.loanapproval.services;

import com.gen.poc.loanapproval.web.dto.LoanRequest;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanSubmitService {

    private final ZeebeClient zeebeClient;

    public String processLoanRequest(LoanRequest request){

        final ProcessInstanceEvent processInstanceEvent = zeebeClient
                .newCreateInstanceCommand().bpmnProcessId("LOAN_APPROVAL_PROCESS")
                .latestVersion().variable("isApplicationComplete", true)
                .send().join();
        return String.valueOf(processInstanceEvent.getProcessInstanceKey());
    }

    public void completeUserTask(String processInstanceKey, String taskName, Map<String, Object> additionalParam){


        Long taskId = (Long) additionalParam.get("taskId");
        zeebeClient.newCompleteCommand(taskId)
                    .variables(additionalParam)
                    .send().join();

    }

    public void acknowledgeDocumentSigning(String loanId, Map<String, Object> additionalParam){

        zeebeClient.newPublishMessageCommand()
                .messageName("MSGEVNT_SIGNED_DOC_RECIEVED")
                .correlationKey("MSGEVNT_SIGNED_DOC_RECIEVED")
                .variables(additionalParam).send().join();
    }

}
