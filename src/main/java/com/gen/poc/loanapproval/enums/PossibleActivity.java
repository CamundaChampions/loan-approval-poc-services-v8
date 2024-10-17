package com.gen.poc.loanapproval.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gen.poc.loanapproval.constants.AppConstants.*;

@Getter
@AllArgsConstructor
public enum PossibleActivity {

    APPLICANT_PENDING_FM_APPROVAL(UserRoleAndUserList.APPLICANT, LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL, List.of(CAN_CANCEL)),
    APPLICANT_PENDING_RM_APPROVAL(UserRoleAndUserList.APPLICANT, LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL, List.of(CAN_CANCEL)),
    APPLICANT_PENDING_DATA_CORRECTION(UserRoleAndUserList.APPLICANT, LoanApplicationStatus.PENDING_DATA_CORRECTION, List.of(CAN_CANCEL, CAN_ACKNOWLEDGE_CORRECTION)),
    APPLICANT_PENDING_AWAITING_MISSING_DOCUMENT(UserRoleAndUserList.APPLICANT, LoanApplicationStatus.AWAITING_MISSING_DOCUMENT, List.of(CAN_CANCEL, CAN_ACKNOWLEDGE_DOC_PROVIDED)),
    FM_PENDING_FM_APPROVAL(UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER, LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL, List.of(CAN_APPROVEORREJECT)),
    RM_PENDING_RM_APPROVAL(UserRoleAndUserList.RISK_ASSESSMENT_MANAGER, LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL, List.of(CAN_APPROVEORREJECT)),

    ;

    private final UserRoleAndUserList userRole;

    private final LoanApplicationStatus status;

    private final List<String> possibleActivity;

    public static List<String> getPossibleActivityByRoleAndStatus(UserRoleAndUserList userRole, LoanApplicationStatus status){
        Optional<PossibleActivity> activityOptional = Arrays.stream(PossibleActivity.values()).filter(activity -> activity.userRole == userRole && activity.status == status)
                .findFirst();

        if(activityOptional.isPresent())
            return activityOptional.get().getPossibleActivity();
        else
            return List.of();
    }
}
