package com.gen.poc.loanapproval.web;

import com.gen.poc.loanapproval.enums.Decision;
import com.gen.poc.loanapproval.enums.UserRoleAndUserList;
import com.gen.poc.loanapproval.exception.NotAuthorizedException;
import com.gen.poc.loanapproval.services.LoanSubmitService;
import com.gen.poc.loanapproval.web.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.web.dto.LoanSummaryListResponse;
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

        authorizeApplicant(userId, "Only Applicants can apply for loan");

        String response = loanSubmitService.processLoanRequest(userId, requestDTO);

        return ResponseEntity.ok(response);

    }

    @PostMapping(path = "/{loan-id}/action/{action}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approve(@RequestHeader(name = "user-id") String userId,
                                          @PathVariable(name = "loan-id") Long loanId,
                                          @PathVariable(name = "action") Decision decision,
                                          @RequestBody String comments) {

        UserRoleAndUserList userRole = authorizeManagerRole(userId);

        loanSubmitService.completeUserTask(loanId, userId, userRole, decision, comments);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc-signing/acknowledge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acknowledgeDocumentSigning(@PathVariable(name = "loan-id") String loanId,
                                                             @RequestHeader(name = "user-id") String userId,
                                                             @RequestBody Map<String, Object> additionalParam) {

        authorizeApplicant(userId, "Applicants can sign the documents for loan");
        loanSubmitService.acknowledgeDocumentSigning(loanId, additionalParam);
        return ResponseEntity.ok("Success!!");
    }

    @PostMapping(path = "/{loan-id}/doc/re-assessment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> documentReAssessment(@PathVariable(name = "loan-id") String loanId,
                                                       @RequestHeader(name = "user-id") String userId,
                                                       @RequestBody Map<String, Object> additionalParam) {

        authorizeApplicant(userId, "Applicants can submit the documents for re-assessment");

        loanSubmitService.acknowledgeDocumentReAssessment(loanId, additionalParam);

        return ResponseEntity.ok("Success!!");

    }

    @PostMapping(path = "/{loan-id}/doc/update-details", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDocumentDetails(@PathVariable(name = "loan-id") String loanId,
                                                        @RequestBody Map<String, Object> additionalParam,
                                                        @RequestHeader(name = "user-id") String userId,
                                                        @RequestParam(name = "amount", required = false) Long amount,
                                                        @RequestParam(value = "term", required = false) Integer term) {

        authorizeApplicant(userId, "Applicants can update document details");
        loanSubmitService.updateDocumentDetails(loanId, additionalParam, Optional.ofNullable(amount), Optional.ofNullable(term));

        return ResponseEntity.ok("Success!!");

    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanSummaryListResponse> getAllProgressTaskOfUser(
            @RequestHeader(name = "user-id") String userId,
            @RequestParam(name = "includeClosedApplication", required = false) boolean includeClosedApplication) {

        authorizeApplicantForAnyRole(userId);

        return ResponseEntity.ok(loanSubmitService.findAllUserItems(userId, includeClosedApplication));
    }

    @GetMapping(path = "/{loan-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanSummaryResponse> getLoanDetailsById(@RequestHeader(name = "user-id") String userId,
                                                                  @PathVariable(name = "loan-id") Long loanId) {

        authorizeApplicantForAnyRole(userId);

        return ResponseEntity.ok(loanSubmitService.findLoanDetailsByIdAndUser(loanId, userId));
    }

    @GetMapping(path = "/cibil-score", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getLoanDetailsById(@RequestHeader(name = "user-id") String userId) {

        // this will be developed later as per requirement
        return ResponseEntity.ok(830);
    }

    @DeleteMapping(path = "/{loan-id}/action/CANCEL", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelLoanApplication(@RequestHeader(name = "user-id") String userId,
                                                        @PathVariable(name = "loan-id") Long loanId) {

        authorizeApplicant(userId, "You are not authorized to cancel the application");
        loanSubmitService.cancelLoan(loanId, userId);
        return ResponseEntity.ok("Cancelled");
    }


    private void authorizeApplicant(String userId, String message) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        if (userRole != UserRoleAndUserList.APPLICANT)
            throw new NotAuthorizedException(userId, message);

    }

    private UserRoleAndUserList authorizeManagerRole(String userId) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER
                || userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER) {
            return userRole;
        }
        throw new NotAuthorizedException(userId, "Applicants cannot perform manager action on loan");
    }

    private UserRoleAndUserList authorizeFinancialAssessmentManager(String userId, String message) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            throw new NotAuthorizedException(userId, message);

        return userRole;
    }

    private UserRoleAndUserList authorizeRiskAssessmentManager(String userId, String message) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        if (userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER)
            throw new NotAuthorizedException(userId, message);

        return userRole;
    }

    private void authorizeApplicantForAnyRole(String userId) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        if (userRole.getUserList().isEmpty())
            throw new NotAuthorizedException(userId, "User not present in the system");

    }

}
