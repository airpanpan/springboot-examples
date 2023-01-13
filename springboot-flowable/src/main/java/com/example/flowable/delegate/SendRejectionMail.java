package com.example.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 服务任务触发器
 */
public class SendRejectionMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("拒绝流程发送短信");
        System.out.println("delegateExecution.getCurrentActivityId() = " + delegateExecution.getCurrentActivityId());
        System.out.println("delegateExecution.getEventName() = " + delegateExecution.getEventName());
        System.out.println("delegateExecution.getProcessInstanceBusinessKey() = " + delegateExecution.getProcessInstanceBusinessKey());

    }
}
