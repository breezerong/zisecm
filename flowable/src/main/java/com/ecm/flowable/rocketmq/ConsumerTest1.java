package com.ecm.flowable.rocketmq;

import com.ecm.flowable.annotation.RocketConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketConsume(topic = "TEST1", tags = "tag1")
public class ConsumerTest1 extends RocketMQListener<String> {

    @Override
    public boolean consume(String s) {
        System.out.println("接收成功" +  s);
        return true;
    }
}
