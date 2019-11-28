package com.ecm.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 10:14 2018/4/25
 * @Modified By:
 */
public class ApplyExecutorListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("提交申请");
    }
}
