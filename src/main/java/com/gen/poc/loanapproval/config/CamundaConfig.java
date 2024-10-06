package com.gen.poc.loanapproval.config;

import io.camunda.common.auth.*;
import io.camunda.tasklist.CamundaTaskListClient;
import io.camunda.tasklist.exception.TaskListException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {

    @Bean
    public CamundaTaskListClient camundaTaskListClient(@Value("${camunda-task-list.restAddress:test}") String taskListRestHost) throws TaskListException {
        SimpleConfig simpleConfig = new SimpleConfig();
        simpleConfig.addProduct(Product.TASKLIST, new SimpleCredential(taskListRestHost, "demo", "demo"));
        Authentication authentication = SimpleAuthentication.builder()
                .withSimpleConfig(simpleConfig).build();
        return CamundaTaskListClient.builder()
                .taskListUrl(taskListRestHost)
                .shouldReturnVariables()
                .shouldLoadTruncatedVariables()
                .authentication(authentication)
                .build();
    }
}
