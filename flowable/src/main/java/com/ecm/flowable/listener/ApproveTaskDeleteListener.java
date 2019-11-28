package com.ecm.flowable.listener;

import com.sun.javafx.tk.Toolkit;
import org.flowable.bpmn.model.Task;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 10:14 2018/4/25
 * @Modified By:
 */
public class ApproveTaskDeleteListener implements TaskListener,Serializable {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("ApproveTaskDeleteListener");
    }

    public void testBean(DelegateTask delegateTask) {
        System.out.println("testBean_ApproveTaskDeleteListener");
    }
}
