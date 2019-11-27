package com.ecm.flowable.rocketmq;

import com.ecm.flowable.annotation.RocketConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RocketConsume(topic = "FLOWABLE", tags = "RETURN")
public class FlowableConsumer extends RocketMQListener<HashMap> {

    @Override
    public boolean consume(HashMap s) {
        System.out.println("接收成功" +  s.get("content"));
        return true;
    }
}
