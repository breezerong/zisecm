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
public class ApproveTaskCompleteListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("ApproveTaskCompleteListener");
    }
}
