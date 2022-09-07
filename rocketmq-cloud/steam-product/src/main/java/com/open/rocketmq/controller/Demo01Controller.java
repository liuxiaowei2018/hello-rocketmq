package com.open.rocketmq.controller;

import com.alibaba.fastjson.JSON;
import com.open.rocketmq.controller.transaction.Args;
import com.open.rocketmq.event.Demo01Message;
import com.open.rocketmq.event.MySource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 20:16
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/demo01")
public class Demo01Controller {

    @Autowired
    private MySource mySource;

    @GetMapping("/send")
    public boolean send() {
        Demo01Message message = new Demo01Message();
        message.setId(new Random().nextInt());
        // 创建 Spring Message 对象
        Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                .build();
        // 发送消息
        return mySource.demo01Output().send(springMessage);
    }

    @GetMapping("/send_delay")
    public boolean sendDelay() {
        Demo01Message message = new Demo01Message();
        message.setId(new Random().nextInt());
        // 创建 Spring Message 对象
        Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                // 设置延迟级别为3 - 10秒后消费
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, "3")
                .build();
        // 发送消息
        boolean sendResult = mySource.demo01Output().send(springMessage);
        log.info("[sendDelay][发送消息完成, 结果 = {}]", sendResult);
        return sendResult;
    }

    /**
     * 过滤消息
     *  -生产者在header里设置tag
     *  -消费者配置 consumer.tag 为需要消费的tag(多个 Tag 使用 || 分隔，默认为空)
     *  -过滤掉的消息，后续是无法被消费掉了(效果和消费成功是一样的)
     * ps.RocketMQ 独有的 Broker级别的消息过滤机制，而 Spring Cloud Stream 提供了通用的 Consumer 级别的效率过滤器机制。
     *  -我们只需要使用 @StreamListener 注解的 condition 属性，设置消息满足指定 Spring EL 表达式的情况下，才进行消费。
     * @date 2022/9/7 11:15
     * @return boolean
     */
    @GetMapping("/send_tag")
    public boolean sendTag() {
        for (String tag : new String[]{"x", "xx", "xxx"}) {
            Demo01Message message = new Demo01Message();
            message.setId(new Random().nextInt());
            // 创建 Spring Message 对象
            Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                    // 设置 Tag
                    .setHeader(MessageConst.PROPERTY_TAGS, tag)
                    .build();
            // 发送消息
            mySource.demo01Output().send(springMessage);
        }
        return true;
    }

    /**
     * 顺序消息
     * 1.需要生产者指定分区key(payload['id']) [分区顺序就是普通顺序消息],表示 id 为 Demo01Message.id
     *  - 全局顺序就是完全严格顺序 可以不指定?
     *  - 如果想从消息的 headers 中获得 Sharding key，可以设置为 headers['partitionKey']
     * 2.消费者开启orderly: true
     * @date 2022/9/6 20:55
     * @return boolean
     */
    @GetMapping("/send_orderly")
    public boolean sendOrderly() {
        // 发送 3 条相同 id 的消息
        int id = new Random().nextInt();
        for (int i = 0; i < 3; i++) {
            Demo01Message message = new Demo01Message();
            message.setId(id);
            // 创建 Spring Message 对象
            Message<Demo01Message> springMessage = MessageBuilder.withPayload(message).build();
            // 发送消息
            mySource.demo01Output().send(springMessage);
        }
        return true;
    }

    @GetMapping("/send_transaction")
    public boolean sendTransaction() {
        // 创建 Message
        Demo01Message message = new Demo01Message();
        message.setId(new Random().nextInt());
        // 创建 Spring Message 对象
        Args args = new Args();
        args.setArgs1(1);
        args.setArgs2("2");
        Message<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                // Spring Cloud Stream 在设计时，并没有考虑事务消息，所以通过 Header 传递参数
                .setHeader("args", JSON.toJSONString(args))
                .build();
        // 发送消息
        return mySource.demo01Output().send(springMessage);
    }

}
