package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApprovalTaskRepository extends JpaRepository<LoanApprovalTask, String> {
    // Custom query methods can be added here if needed

    @Query(value = """
            select * from Loan_Approval_task
            where task_id = :task_id
            And STATUS = 'IN_PROGRESS'
            """, nativeQuery = true)
    LoanApprovalTask findPendingApprovalTask(@Param("task_id") String taskId);

    List<LoanApprovalTask> findByLoanApplicationId(Long loanApplicationId);

    List<LoanApprovalTask> findByLoanApplicationIdAndStatus(Long loanApplicationId, TaskStatus status);
}
