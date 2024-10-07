package com.gen.poc.loanapproval.web.dto;

import lombok.Data;

@Data
public class LoanDocumentDTO {
	private String documentType;
	private String fileName;
	private String filePath;
}