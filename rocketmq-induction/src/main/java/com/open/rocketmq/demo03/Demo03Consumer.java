package com.open.rocketmq.demo03;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo03Message.TOPIC, consumerGroup = "demo03-consumer-group-" + Demo03Message.TOPIC)
public class Demo03Consumer implements RocketMQListener<Demo03Message> {

    @Override
    public void onMessage(Demo03Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
