package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1915:52
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CodeDetail implements Serializable {

    /**
     * 码段
     * */
    private String code;

    /**
     * 状态
     * */
    private int status;

    /**
     * 牌号
     * */
    private String brandName;

    /**
     * 机台号
     * */
    private String machineCode;

    /**
     * 读码时间
     * */
    private String scanDate;

    /**
     * 验证时间
     * */
    private int validateStatus;


}
