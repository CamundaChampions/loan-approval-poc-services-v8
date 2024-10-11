package com.gen.poc.loanapproval.web.dto;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.LoanCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Data
@Validated
public class LoanRequestDTO {

	private LoanCategory loanCategory;

	@NotNull
	@Min(value = 100000)
	private BigDecimal amount;

	private Integer term;

	private String reason;

	private String comments;

}