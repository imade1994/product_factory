package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/717:44
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class IllegalCode implements Serializable {


    private String qrCode;

    private String packageType;

    private String illegalType;

    private String illegalCounts;

    private String scanDate;

    private String scanTime;

}
