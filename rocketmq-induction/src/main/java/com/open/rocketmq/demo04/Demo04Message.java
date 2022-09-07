package com.open.rocketmq.demo04;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo04Message {

    public static final String TOPIC = "DEMO_04";

    /**
     * 编号
     */
    private Integer id;
}
