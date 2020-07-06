package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1510:42
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class Machine implements Serializable {
    /**
     * 机台号
     * */
    private String machineCode;


    /**
     * 机台类型
     * 1:包装机 2：卷包机
     * */
    private String type;

    /**
     * 机组
     * */
    private String machineGroup;


    /**
     * 车间
     *
     * */
    private String room;


    /**
     * 机台运行状态
     * */
    private int status;

}
