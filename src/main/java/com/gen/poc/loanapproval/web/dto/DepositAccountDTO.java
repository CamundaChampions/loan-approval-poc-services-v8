package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositAccountDTO {
	private String accountNumber;
	private String bankName;
	private BigDecimal amountCredited;
	private String accountType;
}
