package com.gen.poc.loanapproval.repository.entity;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "LOAN_APPLICATION")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_application_id")
	private Long loanApplicationId;

	@Column(name = "loan_category")
	private String loanCategory;

	@Column(name = "Amount")
	private BigDecimal amount;

	@Column(name = "Term")
	private Integer term;

	@Column(name = "Reason")
	private String reason;

	@Column(name = "Status")
	@Enumerated(value = EnumType.STRING)
	private LoanApplicationStatus status;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "process_instance_id")
	private String processInstanceId;

}
