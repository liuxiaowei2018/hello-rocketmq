package com.open.rocketmq.demo06;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo06Message {

    public static final String TOPIC = "DEMO_06";

    /**
     * 编号
     */
    private Integer id;
}
