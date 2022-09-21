package com.open.rocketmq.demo03;

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
 * @date 2022年09月06日 17:54
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRocketMqApplication.class)
public class Demo03ProducerTest {

    @Autowired
    private Demo03Producer producer;

    @Test
    public void testSyncSendDelay() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSendDelay(id, 3); // 延迟级别 3 ，即 10 秒后消费
        log.info("[testSyncSendDelay][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSendDelay() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSendDelay(id, 3, new SendCallback() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
            }
            @Override
            public void onException(Throwable e) {
                log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }
        });
        // 阻塞等待，保证消费 -> 发送的消息，延迟 10 秒被 Demo03Consumer 消费
        new CountDownLatch(1).await();
    }
}