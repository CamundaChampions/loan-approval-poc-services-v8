package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashDepositCollateralDTO {
	private String bankName;
	private String accountNumber;
	private BigDecimal amountBalance;
}
