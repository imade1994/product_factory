package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:35
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Verification implements Serializable {
    /**
     * 主键
     * */
    private String id;

    /**
     *
     * 质检工单号
     * */
    private String verificationNumber;

    /**
     * 质检类型
     * */
    private String verificationType;

    /**
     * 抽样时间
     * */
    private String simpleDate;

    /**
     * 样本数量
     * */
    private String simpleCount;

    /**
     * 扣分值
     *
     * */
    private String score;

    /**
     * 检验人
     * */
    private String verificationPeople;

    /**
     * 备注
     * */
    private String remark;

    private String batchNumber;
    /**
     * 扣分，检验子项
     * */
    private List<VerificationDetails> verificationDetails;
}
