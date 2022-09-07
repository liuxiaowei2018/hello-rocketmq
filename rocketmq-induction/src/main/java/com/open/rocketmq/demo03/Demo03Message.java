package com.open.rocketmq.demo03;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月06日 16:52
 * @Description
 */
@Data
public class Demo03Message {

    public static final String TOPIC = "DEMO_03";

    /**
     * 编号
     */
    private Integer id;
}
