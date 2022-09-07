package com.open.rocketmq.demo02;

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
@RocketMQMessageListener(topic = Demo02Message.TOPIC, consumerGroup = "demo02-consumer-group-" + Demo02Message.TOPIC)
public class Demo02Consumer implements RocketMQListener<Demo02Message> {

    @Override
    public void onMessage(Demo02Message message) {
        // 批量发送-逐条消费
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
