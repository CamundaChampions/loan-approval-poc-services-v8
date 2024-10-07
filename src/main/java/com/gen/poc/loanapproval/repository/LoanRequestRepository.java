package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.repository.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
}
