package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApprovalTaskRepository extends JpaRepository<LoanApprovalTask, String> {
    // Custom query methods can be added here if needed

    List<LoanApprovalTask> findByLoanApplicationIdAndTaskCategory(Long loanApplicationId, ApprovalCategory taskCategory);

    List<LoanApprovalTask> findByLoanApplicationId(Long loanApplicationId);
}
