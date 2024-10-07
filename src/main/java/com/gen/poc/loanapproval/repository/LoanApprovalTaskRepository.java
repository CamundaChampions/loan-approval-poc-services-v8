package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApprovalTaskRepository extends JpaRepository<LoanApprovalTask, String> {
    // Custom query methods can be added here if needed
}
