package com.gen.poc.loanapproval.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    CANCELLED("Cancelled"),
    IN_PROGRESS("In-progress"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
}
