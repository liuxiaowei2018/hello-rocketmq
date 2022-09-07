package com.open.rocketmq.demo06;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description 顺序消息
 * RocketMQ 提供了两种顺序级别：
 *  -普通顺序消息 ：Producer 将相关联的消息发送到相同的消息队列。
 *  -完全严格顺序 ：在【普通顺序消息】的基础上，Consumer 严格顺序消费。
 * 目前已知的应用只有数据库 binlog 同步强依赖严格顺序消息，其他应用绝大部分都可以容忍短暂乱序，推荐使用普通的顺序消息。
 * RocketMQ 官方文档对这两种顺序级别的定义：
 * -普通顺序消费模式下，消费者通过同一个消费队列收到的消息是有顺序的，不同消息队列收到的消息则可能是无顺序的。
 * -- RocketMQTemplate 在发送顺序消息时，默认采用 SelectMessageQueueByHash 策略。如此，相同的 hashKey 的消息，就可以发送到相同的 Topic 的对应队列中
 * -严格顺序消息模式下，消费者收到的所有消息均是有顺序的。
 *
 */
@Component
public class Demo06Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSendOrderly(Integer id) {
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送顺序消息
        // 传入方法参数 hashKey ，作为选择消息队列的键(一般情况下，可以使用订单号、商品号、用户编号)
        return rocketMQTemplate.syncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }

    public void asyncSendOrderly(Integer id, SendCallback callback) {
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 异步发送顺序消息
        rocketMQTemplate.asyncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id), callback);
    }

    public void onewaySendOrderly(Integer id) {
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 发送单向顺序消息
        rocketMQTemplate.sendOneWayOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }

}
