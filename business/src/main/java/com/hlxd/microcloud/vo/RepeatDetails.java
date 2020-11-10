package com.hlxd.microcloud.vo;

import lombok.Data;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/1415:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RepeatDetails {

    /**
     * 二维码
     * */
    private String qrCode;

    /**
     * 父二维码
     * */
    private String parentCode;
    /**
     * 生产日期
     * */
    private String relationDate;
    /**
     * 产品名
     * */
    private String productName;

    /**
     * 设备名
     * */
    private String machineName;

    /**
     * 班组名
     * */
    private String shiftName;


}
