package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

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
    private String qrCode;

    /**
     * 状态
     * 状态 0 仓库 1 生产中 2 已退回
     * */
    private int codeStatus;

    /**
     * 批次号
     *
     * */
    private String batchNo;

    /**
     * 包装类型
     * */
    private BigInteger packageType;

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
