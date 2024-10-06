package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {

    private String loanType;

    private BigDecimal loanAmount;

}
