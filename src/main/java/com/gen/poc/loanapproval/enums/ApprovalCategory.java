package com.gen.poc.loanapproval.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalCategory {

    FINANCIAL_ASSESSMENT_MANAGER("Financial Assessment Manager"),
    RISK_ASSESSMENT_MANAGER("Risk Assessment Manager"),

    ;

    private final String displayName;

}
