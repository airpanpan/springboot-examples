package com.example.flowable.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
@Slf4j
public class SendJuniorRejectionMailDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String requestUser = (String) delegateExecution.getVariable("requestUser");
        String resourceId = (String) delegateExecution.getVariable("resourceId");
        System.out.println("SendJuniorRejectionMailDelegate");
        log.info("send approval success mail for user [" + requestUser + "] with apply resource [" + resourceId + "]");
    }
}
