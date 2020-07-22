package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1516:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CancelRelation implements Serializable {
    /**
     * id
     * */
    private BigInteger id;

    /**
     * 编码
     * */
    private String qrCode;

    /**
     * 上层编码
     * */
    private String parentCode;


    /**
     * 解除日期
     * */
    private String cancelDate;


    /**
     * 机台号
     * */
    private String machineCode;

    /**
     * 班组ID
     * */
    private BigInteger shiftId;


    /**
     * 班组名
     * */
    private String shiftName;


    /**
     * 关联关系
     * */
    private int relationType;



    /**
     * 子编码集合
     * */
    private List<CancelRelation> cancelRelations;






}
