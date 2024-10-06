package com.gen.poc.loanapproval.services;

import com.gen.poc.loanapproval.web.dto.LoanRequest;
import io.camunda.tasklist.CamundaTaskListClient;
import io.camunda.tasklist.dto.Task;
import io.camunda.tasklist.dto.TaskList;
import io.camunda.tasklist.dto.TaskSearch;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanSubmitService {

    private final ZeebeClient zeebeClient;

    private final CamundaTaskListClient taskListClient;

    public String processLoanRequest(LoanRequest request){

        final ProcessInstanceEvent processInstanceEvent = zeebeClient
                .newCreateInstanceCommand().bpmnProcessId("LOAN_APPROVAL_PROCESS")
                .latestVersion().variable("isApplicationComplete", true)
                .send().join();
        return String.valueOf(processInstanceEvent.getProcessInstanceKey());
    }

    public void completeUserTask(String processInstanceKey, String taskName, Map<String, Object> additionalParam){
        TaskSearch taskSearch = new TaskSearch()
                .setProcessInstanceKey(processInstanceKey)
                .setTaskDefinitionId(taskName)
                .setState(TaskState.CREATED);
        try {
            Optional<Task> awaitingUserTask = searchTask(taskSearch);
            awaitingUserTask.ifPresent(task -> {
                try {
                    taskListClient.completeTask(task.getId(), additionalParam);
                } catch (TaskListException e) {
                    throw new RuntimeException(e);
                }
//                zeebeClient.newCompleteCommand(Long.parseLong(task.getId()))
//                        .variables(additionalParam)
//                        .send().join();
            });
        } catch (TaskListException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Task> searchTask(TaskSearch taskSearch) throws TaskListException {
        TaskList taskList = taskListClient.getTasks(taskSearch);
        return taskList.size() > 0 ? Optional.empty() : Optional.of(taskList.first());

    }
}
