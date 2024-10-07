package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LoanRequestDTO {
	private BigDecimal amount;
	private Integer term;
	private String reason;
	private List<LoanDocumentDTO> loanDocuments;
	private CashDepositCollateralDTO cashDepositCollateral;
	private CustomerDTO customer;
}