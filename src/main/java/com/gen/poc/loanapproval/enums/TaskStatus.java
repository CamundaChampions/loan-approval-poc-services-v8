package com.gen.poc.loanapproval.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
}
