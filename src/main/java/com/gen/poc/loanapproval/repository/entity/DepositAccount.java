package com.gen.poc.loanapproval.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Deposit_Account")
public class DepositAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Account_Number")
	private String accountNumber;

	@Column(name = "Bank_Name")
	private String bankName;

	@Column(name = "Amount_Credited")
	private BigDecimal amountCredited;

	@Column(name = "Account_Type")
	private String accountType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Customer customer;

}
