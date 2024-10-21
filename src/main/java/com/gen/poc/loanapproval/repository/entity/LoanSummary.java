package com.gen.poc.loanapproval.repository.entity;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.LoanCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class LoanSummary {

    @Id
    @Column(name = "loan_application_Id")
    private BigDecimal loanApplicationId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "loan_category")
    private LoanCategory loanType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private LoanApplicationStatus statusCode;

    @Column(name = "amount")
    public BigDecimal amount;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "term")
    private int term;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_category")
    private ApprovalCategory taskCategory;
}
