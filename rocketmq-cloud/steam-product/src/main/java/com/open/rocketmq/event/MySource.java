package com.open.rocketmq.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:51
 * @Description
 */
public interface MySource {

    @Output("demo01-output")
    MessageChannel demo01Output();

}
