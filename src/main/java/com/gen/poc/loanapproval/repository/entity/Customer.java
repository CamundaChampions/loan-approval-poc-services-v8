package com.gen.poc.loanapproval.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_Name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "ssn")
	private String ssn;

	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Address address;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Employment employment;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private DepositAccount depositAccount;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private LoanRequest loanRequest;

	public void setAddress(Address address) {
		if (this.address != null) {
			this.address.setCustomer(null);
		}
		if (address != null) {
			address.setCustomer(this);
		}
		this.address = address;
	}

	public void setDepositAccount(DepositAccount depositAccount) {
		if (this.depositAccount != null) {
			this.depositAccount.setCustomer(null);
		}
		if (depositAccount != null) {
			depositAccount.setCustomer(this);
		}
		this.depositAccount = depositAccount;
	}

	public void setEmployment(Employment employment) {
		if (this.employment != null) {
			this.employment.setCustomer(null);
		}
		if (employment != null) {
			employment.setCustomer(this);
		}
		this.employment = employment;
	}

}
