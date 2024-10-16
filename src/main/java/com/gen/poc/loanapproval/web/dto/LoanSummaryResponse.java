package com.gen.poc.loanapproval.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class LoanSummaryResponse extends LoanSummaryDto {

    private List<String> possibleActivity;

}
