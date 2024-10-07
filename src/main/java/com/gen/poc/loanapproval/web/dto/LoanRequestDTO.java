package com.gen.poc.loanapproval.web.dto;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LoanRequestDTO {

	private String loanCategory;

	private BigDecimal amount;

	private Integer term;

	private String reason;

	private String comments;

	private String customerId;

	private String processInstanceId;
}