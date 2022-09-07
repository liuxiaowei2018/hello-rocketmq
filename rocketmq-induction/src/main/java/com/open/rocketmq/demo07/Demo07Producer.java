package com.open.rocketmq.demo07;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:54
 * @Description 事务消息
 * RocketMQ 提供事务回查机制，如果应用超过一定时长未 commit 或 rollback 这条事务消息
 * RocketMQ 会主动回查应用，询问这条事务消息是 commit 还是 rollback
 * 从而实现事务消息的状态最终能够被 commit 或是 rollback ，达到最终事务的一致性
 */
@Component
public class Demo07Producer {

    private static final String TX_PRODUCER_GROUP = "demo07-producer-group";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public TransactionSendResult sendMessageInTransaction(Integer id) {
        Demo07Message demo07Message = new Demo07Message();
        demo07Message.setId(id);
        Message message = MessageBuilder.withPayload(demo07Message).build();
        // rocketMQTemplate.sendMessageInTransaction(...)
        // 方法参数 txProducerGroup:事务消息的生产者分组
        //  - (RocketMQ 是回查（请求）指定指定生产分组下的 Producer ，从而获得事务消息的状态，所以一定要正确设置)
        // 方法参数 destination:消息的 Topic + Tag
        // 方法参数 message:消息
        // 方法参数 arg ，后续我们调用本地事务方法的时候，会传入该 arg
        //  - 如果要传递多个方法参数给本地事务的方法，可以通过数组，例如说 Object[]{arg1, arg2, arg3} 这样的形式
        return rocketMQTemplate.sendMessageInTransaction(TX_PRODUCER_GROUP, Demo07Message.TOPIC, message, id);
    }

    @RocketMQTransactionListener(txProducerGroup = TX_PRODUCER_GROUP)
    public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

        private Logger logger = LoggerFactory.getLogger(getClass());
        
        /**
         * 执行本地事务
         * 在调用这个方法之前，RocketMQTemplate 已经使用 Producer 发送了一条事务消息。
         * 然后根据该方法执行的返回的 RocketMQLocalTransactionState 结果，提交还是回滚该事务消息
         * @date 2022/9/6 19:46
         * @param msg
         * @param arg 
         * @return org.apache.rocketmq.spring.core.RocketMQLocalTransactionState
         */
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // ... local transaction process, return rollback, commit or unknown
            logger.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
            // 模拟 RocketMQ 回查 Producer 来获得事务消息的状态，所以返回了 未知状态
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        /**
         * 检查本地事务
         * 事务消息长事件未被提交或回滚时，RocketMQ 会回查事务消息对应的生产者分组下的 Producer ,得事务消息的状态。
         * 此时，该方法就会被调用
         * @date 2022/9/6 19:46
         * @param msg 
         * @return org.apache.rocketmq.spring.core.RocketMQLocalTransactionState
         */
        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            logger.info("[checkLocalTransaction][回查消息：{}]", msg);
            // 直接返回 RocketMQLocalTransactionState.COMMIT 提交状态
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
