package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2116:05
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class ProCode implements Serializable {

    /**
     * 二维码
     * */
    private String qrCode;

    //private ProCode parentDetails;
    /**
     * 父二维码
     * */
    private String parentCode;
    /**
     * 生产日期
     * */
    private String time;
    /**
     * 产品ID
     * */
    private String productId;
    /**
     * 产品名
     * */
    private String productName;
    /**
     * 设备编码
     * */
    private String machineCode;
    /**
     * 设备名
     * */
    private String machineName;
    /**
     * 验证状态
     * */
    private String verifyStatus;
    /**
     * 包装规格
     * */
    private int  type;
    /**
     * 备注
     * */
    private String remark;

    /**
     * 无用字段
     * */
    private String produceDate;


    /***
     * 运输数据
     * *//*
    private Logistics logistics;

    *//**
     * 卷包工单数据
     *
     * *//*
    private WrapOrder wrapOrder;

    *//**
     * 制丝工单数据
     * *//*
    private Throwing throwing;*/



    /**
     * 子集合
     * */
    private List<ProCode> proCodes;




}
