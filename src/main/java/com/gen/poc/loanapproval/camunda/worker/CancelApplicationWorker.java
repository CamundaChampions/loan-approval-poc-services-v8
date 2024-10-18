package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.TaskStatus;
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
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CancelApplicationWorker {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    @JobWorker(type = "cancelApplicationServiceTask", autoComplete = true)
    public void cancelApplicationServiceTask(final JobClient client, final ActivatedJob job){
        Map<String, Object> variables = job.getVariablesAsMap();
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        cancelLoanApplication(loanApplicationId, LoanApplicationStatus.CANCELLED);
    }

    @JobWorker(type = "autoCloseServiceTask", autoComplete = true)
    public void autoCancelApplicationServiceTask(final JobClient client, final ActivatedJob job){
        Map<String, Object> variables = job.getVariablesAsMap();
        Long loanApplicationId = Long.valueOf((Integer) job.getVariablesAsMap().get("loan-id"));
        cancelLoanApplication(loanApplicationId, LoanApplicationStatus.AUTO_CANCELLED);
        log.info("test cancelApplicationServiceTask worker");
    }



    private void cancelLoanApplication(long loanApplicationId, LoanApplicationStatus status){
        Optional<LoanApplication> loanApplicationOptional = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplicationOptional.isEmpty())
            throw new RuntimeException("Invalid Loan Id");


        LoanApplication loanApplication = loanApplicationOptional.get();

        loanApplication.setStatus(status);
        loanApplicationRepository.save(loanApplication);

        List<LoanApprovalTask> loanApprovalTaskList = loanApprovalTaskRepository.findByLoanApplicationIdAndStatus(loanApplicationId, TaskStatus.IN_PROGRESS);
        if(!CollectionUtils.isEmpty(loanApprovalTaskList)) {
            loanApprovalTaskList.forEach(task -> task.setStatus(TaskStatus.CANCELLED));
            loanApprovalTaskRepository.saveAll(loanApprovalTaskList);
        }
    }
}
