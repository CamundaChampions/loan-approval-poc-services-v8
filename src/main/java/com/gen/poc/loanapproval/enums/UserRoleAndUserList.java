package com.gen.poc.loanapproval.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum UserRoleAndUserList {

    NONE(List.of()),
    APPLICANT(List.of("applicant_1", "applicant_2", "applicant_3", "applicant_4")),
    FINANCIAL_ASSESSMENT_MANAGER(List.of("financial_assessment_manager")),
    RISK_ASSESSMENT_MANAGER(List.of("risk_assessment_manager")),
    ;

    private final List<String> userList;

    public static UserRoleAndUserList getUserRole(String user){
        return Arrays.stream(UserRoleAndUserList.values())
                .filter(userList -> userList.getUserList().contains(user)).findFirst().orElse(NONE);
    }
}
