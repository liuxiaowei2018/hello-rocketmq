package com.open.rocketmq.demo03;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description
 */
@Component
public class Demo03Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSendDelay(Integer id, int delayLevel) {
        Demo03Message demo03Message = new Demo03Message();
        demo03Message.setId(id);
        Message message = MessageBuilder.withPayload(demo03Message).build();
        // 同步发送消息 timeout业务超时时间
        return rocketMQTemplate.syncSend(Demo03Message.TOPIC, message, 30 * 1000, delayLevel);
    }

    public void asyncSendDelay(Integer id, int delayLevel, SendCallback callback) {
        Demo03Message demo03Message = new Demo03Message();
        demo03Message.setId(id);
        Message message = MessageBuilder.withPayload(demo03Message).build();
        // 异步发送消息
        rocketMQTemplate.asyncSend(Demo03Message.TOPIC, message, callback, 30 * 1000, delayLevel);
    }
}
