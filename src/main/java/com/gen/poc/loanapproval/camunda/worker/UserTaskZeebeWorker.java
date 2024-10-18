package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserTaskZeebeWorker {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    @JobWorker(type = "io.camunda.zeebe:userTask", autoComplete = false)
    public void handleJob(final JobClient client, final ActivatedJob job) {
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        String taskType = job.getVariablesAsMap().get("taskType").toString();
        LoanApplicationStatus status;
        String taskId;
        ApprovalCategory approvalCategory;
        if(ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER.name().equalsIgnoreCase(taskType)){
            approvalCategory = ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER;
            taskId = "FM-".concat(loanApplicationId.toString());
            status = LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL;
        } else {
            approvalCategory = ApprovalCategory.RISK_ASSESSMENT_MANAGER;
            taskId = "RM-".concat(loanApplicationId.toString());
            status = LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL;
        }
        LoanApprovalTask task = new LoanApprovalTask();
        task.setTaskId(taskId);
        task.setTaskCategory(approvalCategory);
        task.setTaskInstanceId(String.valueOf(job.getKey()));
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setLoanApplicationId(loanApplicationId);
        loanApprovalTaskRepository.save(task);
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(status);
        loanApplicationRepository.save(loanApplication.get());
        // Element Id
        log.info("user task task element id {}", job.getKey());
        log.info("task element id {}", job);

        // get variables

    }
}
