package com.example.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class CallExternalSystemDelegate implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("\"请假流程通过\" = " + "请假流程通过");
    }
}
