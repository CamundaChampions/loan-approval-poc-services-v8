package com.gen.poc.loanapproval.adapter.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public interface BaseDelegate extends JavaDelegate {

    default Object getValueByKey(DelegateExecution execution, String key) {
        return execution.getVariable(key);
    }

    default Integer getIntegerValueByKey(DelegateExecution execution, String key) {
        Object returnObject = getValueByKey(execution, key);
        return returnObject == null ? null : Integer.parseInt(returnObject.toString());
    }

    default void setValueByKey(DelegateExecution execution, String key, Object value) {
        execution.setVariable(key, value);
    }
}
