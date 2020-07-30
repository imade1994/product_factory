package com.hlxd.microcloud.vo;

import lombok.Data;
import scala.math.BigInt;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1011:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CodeBatchDetails implements Serializable {

    /**
     * id
     * */
    private String id;

    /**
     * 批次
     *
     * */
    private String batchNo;


    /**
     * 二维码
     * */
    private String qrCode;


    /**
     * 批次抽检时间
     * */
    private String batchCheckDate;


    /**
     *
     * 包装类型
     * */
    private BigInteger packageType;


    /**
     *
     * 包装类型值
     * */
    private String packageTypeName;


    /**
     * 抽检状态
     * */
    private int checkStatus;





}
