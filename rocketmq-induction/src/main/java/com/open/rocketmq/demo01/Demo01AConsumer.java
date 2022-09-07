package com.open.rocketmq.demo01;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description
 * 通过 MessageExt 类，我们可以获取到消费的消息的更多信息
 * 例如说消息的所属队列、创建时间等等属性，不过消息的内容(body)就需要自己去反序列化
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Demo01Message.TOPIC, consumerGroup = "demo01-A-consumer-group-" + Demo01Message.TOPIC)
public class Demo01AConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt  message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
