package com.open.rocketmq.demo05;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description
 */
@Component
public class Demo05Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        Demo05Message message = new Demo05Message();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo05Message.TOPIC, message);
    }
}
