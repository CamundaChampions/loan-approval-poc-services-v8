package com.gen.poc.loanapproval.repository.entity;

import com.gen.poc.loanapproval.constant.enums.Role;
import com.gen.poc.loanapproval.constant.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Loan_Approval_task")
@Data
public class LoanApprovalTask {

    @Id
    @Column(name = "task_id", nullable = false, length = 25)
    private String taskId;

    @Column(name = "task_instance_id", nullable = false, length = 50)
    private String taskInstanceId;

    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    @Column(name = "status", nullable = false, length = 10)
    private TaskStatus status;

    @Column(name = "loan_request_id", nullable = false)
    private Integer loanRequestId;
}
