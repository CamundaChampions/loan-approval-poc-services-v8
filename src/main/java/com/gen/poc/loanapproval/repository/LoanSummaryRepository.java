package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import com.gen.poc.loanapproval.repository.entity.LoanSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanSummaryRepository extends JpaRepository<LoanSummary, BigDecimal> {
    // Custom query methods can be added here if needed
    @Query(value = """
            Select LOAN.LOAN_APPLICATION_ID, LOAN_CATEGORY, LOAN.STATUS, AMOUNT, TERM, TASK_ID, TASK_CATEGORY from CAM_POC.Loan_application loan inner join CAM_POC.loan_approval_task task
                        on loan.loan_application_id = task.loan_application_id
            where task.TASK_CATEGORY = :taskCategory
            and loan.status = :loanStatus
            and task.status = 'IN_PROGRESS'
            """, nativeQuery = true)
    List<LoanSummary> getPendingApprovalTaskDetailsByTaskCategory(@Param("taskCategory") String taskCategory, @Param("loanStatus") String loanStatus);

    @Query(value = """
            Select LOAN_APPLICATION_ID, LOAN_CATEGORY, STATUS, AMOUNT, TERM, NULL AS TASK_ID, NULL AS TASK_CATEGORY 
            from Loan_application 
            where (:includeClosedApplication = 1 or status not in  ('APPROVE_AND_DISBURSED','REJECTED','AUTO_CANCELLED','CANCELLED'))
            AND customer_id = :userId
            ORDER BY LOAN_APPLICATION_ID DESC
            """, nativeQuery = true)
    List<LoanSummary> getInProcessLoanApplicationItemsOfApplicant(@Param("userId") String userId, @Param("includeClosedApplication") boolean includeClosedApplication);

    @Query(value = """
            Select LOAN.LOAN_APPLICATION_ID, LOAN_CATEGORY, LOAN.STATUS, AMOUNT, TERM, TASK_ID, TASK_CATEGORY from CAM_POC.Loan_application loan inner join CAM_POC.loan_approval_task task
                        on loan.loan_application_id = task.loan_application_id
            where task.TASK_CATEGORY = :taskCategory
            and loan.status = :loanStatus
            and task.status = 'IN_PROGRESS'
            AND LOAN.LOAN_APPLICATION_ID = :loanId
            """, nativeQuery = true)
    LoanSummary getPendingApprovalTaskDetailsByTaskCategoryAndLoanId(@Param("taskCategory") String taskCategory, @Param("loanStatus") String loanStatus, @Param("loanId") Long loanId);

    @Query(value = """
            Select LOAN_APPLICATION_ID, LOAN_CATEGORY, STATUS, AMOUNT, TERM, NULL AS TASK_ID, NULL AS TASK_CATEGORY 
            from Loan_application 
            where customer_id = :userId
            AND LOAN_APPLICATION_ID = :loanId
            """, nativeQuery = true)
    LoanSummary getInProcessLoanApplicationItemsOfApplicantAndLoanId(@Param("userId") String userId, @Param("loanId") Long loanId);

}
