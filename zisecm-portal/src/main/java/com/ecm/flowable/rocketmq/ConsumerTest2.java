package com.ecm.flowable.rocketmq;

import com.ecm.flowable.annotation.RocketConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketConsume(topic = "TEST2", tags = "tag2")
public class ConsumerTest2 extends RocketMQListener<String> {

    @Override
    public boolean consume(String s) {
        System.out.println("接收成功" +  s);
        return true;
    }
}
