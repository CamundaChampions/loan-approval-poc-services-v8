package com.gen.poc.loanapproval.constant.enums;


import lombok.Getter;

@Getter
public enum Role {
    MANAGER("Manager"),
    FINANCIAL_ASSESSMENT_MANAGER("Financial Assessment Manager");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

}
