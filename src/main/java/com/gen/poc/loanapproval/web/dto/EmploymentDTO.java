package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDTO {
	private String employerName;
	private String jobTitle;
	private BigDecimal annualIncome;
	private Integer experience;
}
