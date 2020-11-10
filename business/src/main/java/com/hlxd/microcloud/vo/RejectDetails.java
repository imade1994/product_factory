package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2516:12
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RejectDetails implements Serializable {


    /**
     * 剔除原因
     * */
    private String rejectReason;


    /**
     * 剔除码
     * */
    private String qrCode;


    /**
     * 剔除时间
     * */
    private String rejectTime;


    /**
     * 班组
     *
     * */
    private String shiftName;


    /**
     * 机台名
     * */
     private String machineName;






}
