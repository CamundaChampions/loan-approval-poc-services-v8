package com.gen.poc.loanapproval.web.dto;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.LoanCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanSummaryDto {

    private BigDecimal loanApplicationId;

    private LoanCategory loanTypeCode;

    public String getLoanType(){
        return loanTypeCode.getDisplayName();
    }

    private LoanApplicationStatus statusCode;

    public String getStatus(){
        if(this.statusCode == null)
            return "";
        return this.statusCode.getDisplayName();
    }

    public BigDecimal amount;

    private String reason;

    private String comments;

    private boolean approvalRequire;

    private String taskId;

    private int term;

    private ApprovalCategory taskCategory;

    private boolean requireApplicantAcknowledgement;
}
