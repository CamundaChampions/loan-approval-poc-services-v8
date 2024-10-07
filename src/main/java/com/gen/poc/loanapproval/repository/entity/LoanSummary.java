package com.gen.poc.loanapproval.repository.entity;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class LoanSummary {

    @Id
    @Column(name = "loan_application_Id")
    private BigDecimal loanApplicationId;

    @Column(name = "loan_category")
    private String loanType;

    @Column(name = "status")
    private LoanApplicationStatus statusCode;

    @Column(name = "amount")
    public BigDecimal amount;

    @Column(name = "task_id")
    private String taskId;
}
