package com.open.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:51
 * @Description
 * 1.正常消费
 * 2.消费异常->异常处理(消费重试)
 *  -在全局和局部异常处理都定义的情况下，错误消息仅会被符合条件的局部错误异常处理。如果没有符合条件的，错误消息才会被全局异常处理。
 *  -注意，如果异常处理方法成功，没有重新抛出异常，会认定为该消息被消费成功，所以就不会进行消费重试
 */
@Slf4j
@Component
public class Demo01Consumer {

    /**
     * 对应 DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01
     * @date 2022/9/6 20:44
     * @param message
     */
    //@StreamListener(value = MySink.DEMO01_INPUT, condition = "headers['rocketmq_TAGS'] == 'x'")
    @StreamListener(MySink.DEMO01_INPUT)
    public void onMessage(@Payload Demo01Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        throw new RuntimeException("当消费异常时该如何处理-消费重试");
    }

    /**
     * 局部的异常处理：通过订阅指定错误 Channel
     * 对应 DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.errors
     * @date 2022/9/6 20:44
     * @param errorMessage
     */
    @ServiceActivator(inputChannel = "DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.errors")
    public void handleError(ErrorMessage errorMessage) {
        log.error("[handleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.error("[handleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[handleError][headers：{}]", errorMessage.getHeaders());
    }

    /**
     * 全局的异常处理：通过订阅全局错误 Channel
     * @date 2022/9/6 20:44
     * @param errorMessage
     */
    @StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
    }

}
