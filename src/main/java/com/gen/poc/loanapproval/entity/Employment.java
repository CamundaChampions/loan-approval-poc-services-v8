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
@Table(name = "Employment")
public class Employment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Employer_Name")
	private String employerName;

	@Column(name = "Job_Title")
	private String jobTitle;

	@Column(name = "Annual_Income")
	private BigDecimal annualIncome;

	@Column(name = "Experience")
	private Integer experience;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	@JsonIgnore
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Customer customer;

}
