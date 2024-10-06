package com.gen.poc.loanapproval;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Deployment(resources = "classpath:LOAN_APPROVAL_PROCESS.bpmn")
@SpringBootApplication
public class LoanApprovalPocServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApprovalPocServicesApplication.class, args);
	}

}
