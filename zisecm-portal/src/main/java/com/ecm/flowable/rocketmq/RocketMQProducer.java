package com.ecm.flowable.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.ecm.flowable.properties.RocketMqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 14:06 2018/5/15
 * @Modified By:
 */
@Component
public class RocketMQProducer {

    private static DefaultMQProducer sender;

    @Value("${spring.application.name}")
    private String groupName;

    @Autowired
    private RocketMqProperties properties;

    @PostConstruct
    public void init() {
        sender = new DefaultMQProducer(groupName);
      //  sender.setNamesrvAddr(properties.getNamesrvaddr().get(0));
        try {
            sender.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static<T> void send(String topic,String tags,String keys,T body) {

        String json = JSON.toJSONString(body);
        Message message = new Message(topic,tags,keys,json.getBytes());
        send(message);
    }

    private static void send(Message message) {

        try {
            SendResult result = sender.send(message);
            SendStatus status = result.getSendStatus();
            System.out.println("messageId=" + result.getMsgId() + ", status=" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
