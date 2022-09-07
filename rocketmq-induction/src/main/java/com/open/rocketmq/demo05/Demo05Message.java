package com.open.rocketmq.demo05;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo05Message {

    public static final String TOPIC = "DEMO_05";

    /**
     * 编号
     */
    private Integer id;
}
