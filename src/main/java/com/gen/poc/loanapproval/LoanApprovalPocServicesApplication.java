package com.gen.poc.loanapproval;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.camunda.community.migration.adapter.EnableCamunda7Adapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Deployment(resources = {"classpath:LOAN_APPROVAL_PROCESS.bpmn", "classpath:ADD_INTEGERS.bpmn"})
@SpringBootApplication
@EnableCamunda7Adapter
public class LoanApprovalPocServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApprovalPocServicesApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowedOrigins("*");
			}
		};
	}
}
