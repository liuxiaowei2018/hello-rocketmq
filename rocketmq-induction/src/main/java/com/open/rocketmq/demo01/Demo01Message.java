package com.open.rocketmq.demo01;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo01Message {

    public static final String TOPIC = "DEMO_01";

    /**
     * 编号
     */
    private Integer id;
}
