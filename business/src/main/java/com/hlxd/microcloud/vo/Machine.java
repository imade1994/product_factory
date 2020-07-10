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
     * 机台型号
     *
     * */
    private String machineModel;

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
