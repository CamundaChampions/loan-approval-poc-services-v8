package com.gen.poc.loanapproval.adapter.delegates;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("addIntegersTask")
@Slf4j
@RequiredArgsConstructor
public class AddIntegerTask implements BaseDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("inside addition delegate");

        Integer first = getIntegerValueByKey(delegateExecution, "firstNum");
        Integer second = getIntegerValueByKey(delegateExecution, "secondNum");

        log.info("adding {} and {}", first, second);
        Integer result = first + second;
        setValueByKey(delegateExecution, "result", result);

        log.info("Result {}", result);
    }

}
