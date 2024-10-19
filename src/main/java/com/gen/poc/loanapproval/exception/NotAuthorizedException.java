package com.gen.poc.loanapproval.exception;

import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {
    private final String userId;

    public NotAuthorizedException(String userId, String message) {
        super(message);
        this.userId = userId;
    }
}
