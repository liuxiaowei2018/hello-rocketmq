package com.open.rocketmq.demo06;

import com.open.rocketmq.HelloRocketMqApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 18:25
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRocketMqApplication.class)
public class Demo06ProducerTest {

    @Autowired
    private Demo06Producer producer;

    @Test
    public void testSyncSendOrderly() throws InterruptedException {
        // 发送多条消息
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            SendResult result = producer.syncSendOrderly(id);
            // # Producer 同步发送 3 条顺序消息成功，都发送到了 Topic 为 DEMO_06 ，队列编号为 1 的消息队列上
            log.info("[testSyncSendOrderly][发送编号：[{}] 发送结果：[{}]]", id, result);
        }
        // 阻塞等待，保证消费
        // Consumer 顺序消费消息时，是在单线程中，顺序消费每条消息
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.asyncSendOrderly(id, new SendCallback() {
                @Override
                public void onSuccess(SendResult result) {
                    log.info("[testASyncSendOrderly][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
                }
                @Override
                public void onException(Throwable e) {
                    log.info("[testASyncSendOrderly][发送编号：[{}] 发送异常]]", id, e);
                }
            });
        }
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testOnewaySendOrderly() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int id = 1024; // 固定成 1024 ，方便我们测试是否发送到相同消息队列
            producer.onewaySendOrderly(id);
            log.info("[testOnewaySendOrderly][发送编号：[{}] 发送完成]", id);
        }
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}