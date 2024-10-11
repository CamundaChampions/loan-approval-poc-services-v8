package com.gen.poc.loanapproval.repository.entity;

import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.enums.ApprovalCategory;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Loan_Approval_task")
@Data
public class LoanApprovalTask {

    @Id
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_category")
    @Enumerated(value = EnumType.STRING)
    private ApprovalCategory taskCategory;

    @Column(name = "loan_application_id")
    private Long loanApplicationId;

    @Column(name = "task_instance_id")
    private String taskInstanceId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

}
