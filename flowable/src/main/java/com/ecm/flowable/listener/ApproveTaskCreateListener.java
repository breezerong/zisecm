package com.ecm.flowable.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Arrays;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 10:14 2018/4/25
 * @Modified By:
 */
public class ApproveTaskCreateListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String [] candidateUsers={"a","b","c"};
        delegateTask.setVariable("approveusers", Arrays.asList(candidateUsers));
        System.out.println("ApproveTaskCreateListener");
    }
}
