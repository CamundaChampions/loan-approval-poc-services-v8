package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    @Modifying
    @Query(value = """
            update Loan_application 
            set status = :status
            where loan_application_id = :loanApplicationId
            """, nativeQuery = true)
    void uploadLoanStatus(@Param("loanApplicationId") long loanApplicationId, @Param("status") String status);

}
