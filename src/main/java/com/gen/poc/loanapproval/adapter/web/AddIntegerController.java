package com.gen.poc.loanapproval.adapter.web;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AddIntegerController {

    private final ZeebeClient zeebeClient;

    @GetMapping(path = "/add", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createLoanRequestAndReturnRequestId(@RequestParam("first") Integer first,
                                                                      @RequestParam("second") Integer second) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("firstNum", first);
        variables.put("secondNum", second);

        ProcessInstanceEvent task = zeebeClient
                .newCreateInstanceCommand().bpmnProcessId("ADD_INTEGERS")
                .latestVersion().variables(variables)
                .send().join();

        return ResponseEntity.ok("Success - process instance key : %s".formatted(task.getProcessInstanceKey()));
    }
}
