package com.gen.poc.loanapproval.enums;

import lombok.Getter;

@Getter
public enum LoanApplicationStatus {
    CREATED("Created"),
    PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL("Pending Financial Assessment Approval"),
    PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL("Pending Risk Assessment Approval"),
    PENDING_DATA_CORRECTION("Pending Data Correction"),
    AWAITING_MISSING_DOCUMENT("Awaiting Missing Document"),
    PENDING_DOCUMENT_SIGNING("Pending Document Signing"),
    APPROVE_AND_DISBURSED("Approve and Disbursed"),
    AUTO_CANCELLED("Auto Cancelled"),
    CANCELLED("Cancelled"),
    REJECTED("Rejected");


    private final String displayName;

    LoanApplicationStatus(String displayName) {
        this.displayName = displayName;
    }
}
