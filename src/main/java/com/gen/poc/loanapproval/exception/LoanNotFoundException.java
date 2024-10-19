package com.gen.poc.loanapproval.exception;

import lombok.Getter;

@Getter
public class LoanNotFoundException extends RuntimeException {
    private final Long loanId;
    public LoanNotFoundException(Long loanId) {
        super("");
        this.loanId = loanId;
    }
}
