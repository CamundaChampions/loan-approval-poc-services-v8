package com.gen.poc.loanapproval.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanCategory {
    CAR_LOAN("Car Loan"),
    HOME_LOAN("Home Loan"),
    PERSONAL_LOAN("Personal Loan"),
    BUSINESS_LOAN("Business Loan"),
    ;

    private final String displayName;
}
