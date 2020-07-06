package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1114:50
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CheckRecord implements Serializable {
    /**
     * id
     * */
    private int id;
    /**
     * 抽检时间
     * */
    private String checkTime;
    /**
     * 抽检结果
     * */
    private int status;
    /**
     * 抽检类型 1：包装机 2：装箱机
     * */

    private int type;
    /**
     * 机台号
     * */

    private String machineCode;


}
