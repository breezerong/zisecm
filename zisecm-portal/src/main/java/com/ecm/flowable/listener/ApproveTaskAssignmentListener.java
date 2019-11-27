package com.ecm.flowable.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.io.Serializable;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 10:14 2018/4/25
 * @Modified By:
 */
public class ApproveTaskAssignmentListener implements TaskListener,Serializable {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("ApproveTaskAssignmentListener");
    }
    public void testBean(DelegateTask delegateTask) {
        System.out.println("testBean_ApproveTaskAssignmentListener");
    }
}
