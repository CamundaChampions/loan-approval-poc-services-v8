package com.gen.poc.loanapproval.web;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.services.LoanSubmitService;
import com.gen.poc.loanapproval.web.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.web.dto.LoanSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/loan")
public class LoanSubmitController {

    private final LoanSubmitService loanSubmitService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLoanRequestAndReturnRequestId(@RequestHeader(name = "user-id") String userId,
                                                                      @RequestBody LoanRequestDTO requestDTO) {

        String response = loanSubmitService.processLoanRequest(userId, requestDTO);

        return ResponseEntity.ok(response);

    }

    @PostMapping(path = "/{loan-id}/approval-type/{approval-type}/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approve(@PathVariable(name = "loan-id") String loanId,
                                          @PathVariable(name = "approval-type") ApprovalCategory approvalType,
                                          @RequestBody Map<String, Object> additionalParam) {

        loanSubmitService.completeUserTask(loanId, approvalType, additionalParam);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc-signing/acknowledge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acknowledgeDocumentSigning(@PathVariable(name = "loan-id") String loanId,
                                                             @RequestBody Map<String, Object> additionalParam) {

        loanSubmitService.acknowledgeDocumentSigning(loanId, additionalParam);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc/re-assessment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> documentReAssessment(@PathVariable(name = "loan-id") String loanId,
                                                       @RequestBody Map<String, Object> additionalParam) {

        loanSubmitService.acknowledgeDocumentReAssessment(loanId, additionalParam);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc/update-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDocumentDetails(@PathVariable(name = "loan-id") String loanId,
                                                        @RequestBody Map<String, Object> additionalParam,
                                                        @RequestParam(name = "amount", required = false) Long amount,
                                                        @RequestParam(value = "term", required = false) Integer term) {

        loanSubmitService.updateDocumentDetails(loanId, additionalParam, Optional.ofNullable(amount), Optional.ofNullable(term));

        return ResponseEntity.ok("Success!!");

    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanSummaryResponse> getAllProgressTaskOfUser(@RequestHeader(name = "user-id") String userId) {

        return ResponseEntity.ok(loanSubmitService.findAllUserItems(userId));
    }
}
