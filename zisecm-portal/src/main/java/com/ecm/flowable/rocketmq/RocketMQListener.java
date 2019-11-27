package com.ecm.flowable.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class RocketMQListener<T> implements MessageListenerConcurrently {

    public abstract boolean consume(T t);

    private boolean consume(MessageExt messageExt) {
        if (null != messageExt) {
            byte[] bytes = messageExt.getBody();
            if (null != bytes) {
                String json = new String(bytes);
                if (StringUtils.isNotEmpty(json)) {
                    Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    T t = JSON.parseObject(json,entityClass);
                    if (!consume(t)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private ConsumeConcurrentlyStatus consume(List<MessageExt> messageExtList) {
        if (null != messageExtList && !messageExtList.isEmpty()) {
            for (MessageExt messageExt : messageExtList) {
                if (!consume(messageExt)) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList,
                                                    ConsumeConcurrentlyContext context) {
        return consume(messageExtList);
    }
}
