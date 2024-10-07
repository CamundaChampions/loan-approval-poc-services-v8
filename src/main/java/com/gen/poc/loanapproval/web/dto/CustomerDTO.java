package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
	private String firstName;
	private String lastName;
	private String ssn;
	private LocalDate dob;
	private String maritalStatus;
	private String email;
	private String phone;
	private AddressDTO address;
	private EmploymentDTO employment;
	private DepositAccountDTO depositAccount;
}
