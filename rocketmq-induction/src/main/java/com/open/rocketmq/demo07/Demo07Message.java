package com.open.rocketmq.demo07;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo07Message {

    public static final String TOPIC = "DEMO_07";

    /**
     * 编号
     */
    private Integer id;
}
