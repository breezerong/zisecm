package com.ecm.flowable.listener;

import com.ecm.flowable.rocketmq.RocketMQProducer;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.HashMap;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 15:00 2018/4/24
 * @Modified By:
 */
public class EndTimerExecutorListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {

        if (delegateExecution.getVariable("isReturn") != null && !(boolean)delegateExecution.getVariable("isReturn")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("content", "请尽快归还");
            RocketMQProducer.send("FLOWABLE", "RETURN", delegateExecution.getId(),map);
        }
    }
}
