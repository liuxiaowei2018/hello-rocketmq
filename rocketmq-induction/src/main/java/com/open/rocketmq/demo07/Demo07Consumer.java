package com.open.rocketmq.demo07;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description demo 1-4 都是集群消费
 * 这里的demo我们采用普通顺序消费
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo07Message.TOPIC,
        consumerGroup = "demo07-consumer-group-" + Demo07Message.TOPIC)
public class Demo07Consumer implements RocketMQListener<Demo07Message> {

    @Override
    public void onMessage(Demo07Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
