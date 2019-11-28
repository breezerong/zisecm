package com.ecm.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 15:00 2018/4/24
 * @Modified By:
 */
public class StartTimerExecutorListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("设置"+delegateExecution.getVariable("duration")+"后到期提醒");
    }
}
