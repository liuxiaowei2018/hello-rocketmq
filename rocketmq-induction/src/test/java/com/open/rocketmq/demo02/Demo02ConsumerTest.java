package com.open.rocketmq.demo02;

import com.open.rocketmq.HelloRocketMqApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 17:27
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRocketMqApplication.class)
public class Demo02ConsumerTest {

    @Autowired
    private Demo02Producer producer;

    @Test
    public void testSendBatch() throws InterruptedException {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        SendResult result = producer.sendBatch(ids);
        log.info("[testSendBatch][发送编号：[{}] 发送结果：[{}]]", ids, result);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}