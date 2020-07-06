package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1115:15
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class BreakRecord implements Serializable {

    /**
     * id
     * */
    private int id;

    /**
     * 机台号
     * */
    private String machineCode;

    /**
     * 停机开始时间
     * */
    private String date;

    /**
     * 班次班组 1：早 2：中 3：晚
     * */
    private int period;

    /**
     * 停机时长
     * */
    private int breakTimeOut;

    /**
     * 停机次数
     * */
    private int breakCount;

    /**
     * 机台类型 1：包装机 2：装箱机
     * */
    private int type;
}
