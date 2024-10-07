package com.gen.poc.loanapproval.web.dto;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanSummaryDto {

    private BigDecimal loanApplicationId;

    private String loanType;

    private LoanApplicationStatus statusCode;

    public String getLoanStatus(){
        if(this.statusCode == null)
            return "";
        return this.statusCode.getDisplayName();
    }

    public BigDecimal amount;

    private boolean approvalRequire;

    private String taskId;
}
