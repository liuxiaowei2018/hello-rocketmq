package com.open.rocketmq.demo06;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
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
@RocketMQMessageListener(topic = Demo06Message.TOPIC,
        consumerGroup = "demo06-consumer-group-" + Demo06Message.TOPIC,
        consumeMode = ConsumeMode.ORDERLY)
public class Demo06Consumer implements RocketMQListener<Demo06Message> {

    @Override
    public void onMessage(Demo06Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // sleep 2 秒，用于查看顺序消费的效果
        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException ignore) {
        }
    }
}
