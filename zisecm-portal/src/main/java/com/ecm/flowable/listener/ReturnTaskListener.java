package com.ecm.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 15:25 2018/4/24
 * @Modified By:
 */
public class ReturnTaskListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        delegateExecution.setVariable("isReturn",true);
        System.out.println("归还成功");
    }
}
