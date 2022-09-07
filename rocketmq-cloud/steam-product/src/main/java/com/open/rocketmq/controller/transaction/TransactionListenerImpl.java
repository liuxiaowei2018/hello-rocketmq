package com.open.rocketmq.controller.transaction;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author liuxiaowei
 * @date 2022年09月07日 11:22
 * @Description
 *  RocketMQ 是回查（请求）指定指定生产分组下的 Producer，从而获得事务消息的状态，所以一定要正确设置的生产者分组
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "test")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object o) {
        // 从消息 Header 中解析到 args 参数，并使用 JSON 反序列化
        Args args = JSON.parseObject(msg.getHeaders().get("args", String.class), Args.class);
        // ... local transaction process, return rollback, commit or unknown
        log.info("[executeLocalTransaction][执行本地事务，消息：{} args：{}]", msg, args);
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("[checkLocalTransaction][回查消息：{}]", msg);
        return RocketMQLocalTransactionState.COMMIT;
    }
}
