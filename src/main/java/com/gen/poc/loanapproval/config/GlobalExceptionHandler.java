package com.gen.poc.loanapproval.config;

import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.exception.NotAuthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String unauthorizedHandler(NotAuthorizedException e) {
        return "User: %s, not authorized for requested resource%s".formatted(e.getUserId(),
                StringUtils.isEmpty(e.getMessage()) ? "." : ", message: %s".formatted(e.getMessage()));
    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String loanNotFoundHandler(LoanNotFoundException e) {
        return "Verify Loan Id: %d".formatted(e.getLoanId());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultHandler(Exception e) {
        return "Unable to process the request, contact application support for error: %s".formatted(e.getMessage());
    }
}
