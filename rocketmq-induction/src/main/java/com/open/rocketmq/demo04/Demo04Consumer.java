package com.open.rocketmq.demo04;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description RocketMQ 提供消费重试的机制。在消息消费失败的时候，RocketMQ 会通过消费重试机制，重新投递该消息给 Consumer
 * 让 Consumer 有机会重新消费消息，实现消费成功
 * 在默认情况下，达到 16 次重试次数时，Consumer 还是消费失败时，该消息就会进入到死信队列
 * 在 RocketMQ 中，可以通过使用 console 控制台对死信队列中的消息进行重发来使得消费者实例再次进行消费
 * 每条消息的失败重试，是有一定的间隔时间.消费重试是基于「定时消息」 来实现，第一次重试消费按照延迟级别为 3 开始
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo04Message.TOPIC, consumerGroup = "demo04-consumer-group-" + Demo04Message.TOPIC)
public class Demo04Consumer implements RocketMQListener<Demo04Message> {

    @Override
    public void onMessage(Demo04Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        throw new RuntimeException("模拟消费失败异常");
    }
}
