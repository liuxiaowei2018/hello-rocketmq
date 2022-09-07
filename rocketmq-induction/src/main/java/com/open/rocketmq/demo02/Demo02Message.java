package com.open.rocketmq.demo02;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo02Message {

    public static final String TOPIC = "DEMO_02";

    /**
     * 编号
     */
    private Integer id;
}
