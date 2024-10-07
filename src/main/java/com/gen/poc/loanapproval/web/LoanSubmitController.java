package com.gen.poc.loanapproval.web;

import com.gen.poc.loanapproval.repository.entity.LoanRequest;
import com.gen.poc.loanapproval.services.LoanSubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/loan")
public class LoanSubmitController {

    private final LoanSubmitService loanSubmitService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLoanRequestAndReturnRequestId(@RequestBody LoanRequest loanRequest) {

        String response = loanSubmitService.processLoanRequest(loanRequest);

        return ResponseEntity.ok(response);

    }

    @PostMapping(path = "/{loan-id}/approval-type/{approval-type}/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approve(@PathVariable(name = "loan-id") String loanId,
                                          @PathVariable(name = "approval-type") String approvalType,
                                          @RequestBody Map<String, Object> additionalParam) {

        loanSubmitService.completeUserTask(loanId,approvalType, additionalParam);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc-signing/acknowledge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acknowledgeDocumentSigning(@PathVariable(name = "loan-id") String loanId,
                                                             @RequestBody Map<String, Object> additionalParam) {

        loanSubmitService.acknowledgeDocumentSigning(loanId, additionalParam);

        return ResponseEntity.ok("Success!!");

    }
}
