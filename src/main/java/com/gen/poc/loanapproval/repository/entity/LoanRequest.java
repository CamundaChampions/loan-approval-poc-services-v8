package com.gen.poc.loanapproval.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "Loan_Request")
public class LoanRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Amount")
	private BigDecimal amount;

	@Column(name = "Term")
	private Integer term;

	@Column(name = "Reason")
	private String reason;

	@Column(name = "Status")
	private String status;

	@Column(name = "Comments")
	private String comments;

	@OneToMany(mappedBy = "loanRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<LoanDocuments> loanDocuments;

	@OneToOne(mappedBy = "loanRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private CashDepositCollateral cashDepositCollateral;

	@Column(name = "Workflow_ID")
	private String workflowId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Customer customer;

	public void setCashDepositCollateral(CashDepositCollateral cashDepositCollateral) {
		if (this.cashDepositCollateral != null) {
			this.cashDepositCollateral.setLoanRequest(null);
		}
		if (cashDepositCollateral != null) {
			cashDepositCollateral.setLoanRequest(this);
		}
		this.cashDepositCollateral = cashDepositCollateral;
	}

	public void setLoanDocuments(List<LoanDocuments> loanDocuments) {
		if (this.loanDocuments != null) {
			this.loanDocuments.forEach(loanDocument -> loanDocument.setLoanRequest(null));
		}
		if (loanDocuments != null) {
			loanDocuments.forEach(loanDocument -> loanDocument.setLoanRequest(this));
		}
		this.loanDocuments = loanDocuments;
	}

	public void addLoanDocument(LoanDocuments loanDocument) {
		loanDocument.setLoanRequest(this);
		loanDocuments.add(loanDocument);
	}

	public void setCustomer(Customer customer) {
		if (this.customer != null) {
			this.customer.setLoanRequest(null);
		}
		if (customer != null) {
			customer.setLoanRequest(this);
		}
		this.customer = customer;
	}

}
