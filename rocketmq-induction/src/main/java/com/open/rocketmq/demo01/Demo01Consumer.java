package com.open.rocketmq.demo01;

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
@RocketMQMessageListener(topic = Demo01Message.TOPIC, consumerGroup = "demo01-consumer-group-" + Demo01Message.TOPIC)
public class Demo01Consumer implements RocketMQListener<Demo01Message> {

    @Override
    public void onMessage(Demo01Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
