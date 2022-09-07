package com.open.rocketmq.demo04;

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
public class Demo04Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        Demo04Message message = new Demo04Message();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo04Message.TOPIC, message);
    }
}
