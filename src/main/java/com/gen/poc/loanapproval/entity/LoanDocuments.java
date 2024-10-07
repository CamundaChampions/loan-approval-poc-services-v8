package com.gen.poc.loanapproval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "Loan_Documents")
public class LoanDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Document_Type")
	private String documentType;

	@Column(name = "File_Name")
	private String fileName;

	@Column(name = "File_Path")
	private String filePath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Loan_Request_ID")
	@JsonIgnore
	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private LoanRequest loanRequest;

}
