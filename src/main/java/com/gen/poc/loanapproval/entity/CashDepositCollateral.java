package com.gen.poc.loanapproval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Cash_Deposit_Collateral")
public class CashDepositCollateral {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Bank_Name")
	private String bankName;

	@Column(name = "Account_Number")
	private String accountNumber;

	@Column(name = "Amount_Balance")
	private BigDecimal amountBalance;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOAN_REQUEST_ID")
	@JsonIgnore
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private LoanRequest loanRequest;

}
