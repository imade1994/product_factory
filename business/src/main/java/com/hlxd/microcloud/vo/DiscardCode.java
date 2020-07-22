package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1515:11
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class DiscardCode implements Serializable {

    /**
     * id
     * */
    private BigInteger id;


    /**
     * 废除类型
     * */
    private String disCardTypeCode;


    /**
     * 废除日期
     * */
    private String disCardDate;

    /**
     * 机台
     * */
    private String machineCode;


    /**
     * 班组ID
     * */
    private String shiftId;


    /**
     * 班组名
     * */
    private String shiftName;



    /**
     * 废码
     * 包码
     * */
    private String qrCode;




    /**
     * 废除时间
     * 日期类型
     * */
    private String discardTime;

    /**
     * 包装类型
     *
     * */
    private int packageType;


}
