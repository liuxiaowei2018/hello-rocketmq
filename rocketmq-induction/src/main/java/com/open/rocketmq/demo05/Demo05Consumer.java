package com.open.rocketmq.demo05;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description demo 1-4 都是集群消费
 * 这里的demo我们采用广播消费
 * 广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo05Message.TOPIC,
        consumerGroup = "demo05-consumer-group-" + Demo05Message.TOPIC,
        messageModel = MessageModel.BROADCASTING)
public class Demo05Consumer implements RocketMQListener<Demo05Message> {

    @Override
    public void onMessage(Demo05Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
