package com.open.rocketmq.demo07;

import com.open.rocketmq.HelloRocketMqApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 19:21
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRocketMqApplication.class)
public class Demo07ProducerTest {

    @Autowired
    private Demo07Producer producer;

    @Test
    public void testSendMessageInTransaction() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.sendMessageInTransaction(id);
        log.info("[testSendMessageInTransaction][发送编号：[{}] 发送结果：[{}]]", id, result);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();

        // 1.TransactionListenerImpl 执行 executeLocalTransaction 方法，先执行本地事务的逻辑
        // 2.Producer 发送事务消息成功，但是因为 executeLocalTransaction 方法返回的是 UNKOWN 状态，所以事务消息并未提交或者回滚
        // 3.RocketMQ Broker 在发送事务消息 30 秒后，发现事务消息还未提交或是回滚，所以回查 Producer,此时，checkLocalTransaction 方法返回 COMMIT ，所以该事务消息被提交
        // 4. 事务消息被提交，所以该消息被 Consumer 消费
    }
}