package com.open.rocketmq.demo02;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description
 */
@Component
public class Demo02Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult sendBatch(Collection<Integer> ids) {
        List<Message> messages = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            Demo02Message message = new Demo02Message();
            message.setId(id);
            messages.add(MessageBuilder.withPayload(message).build());
        }
        // 同步批量发送消息
        return rocketMQTemplate.syncSend(Demo02Message.TOPIC, messages, 30 * 1000L);
    }

}
