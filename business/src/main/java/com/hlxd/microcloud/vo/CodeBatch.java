package com.hlxd.microcloud.vo;

import lombok.Data;
import scala.math.BigInt;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1817:02
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CodeBatch implements Serializable {


    /**
     * 批次号
     * */
    private String batchNo;


    /**
     * 牌号
     * */
    private String brandName;

    /***
     * 小盒码数量
     */
    private BigInteger capsuleNum;

    /**
     * 条盒码数量
     * */
    private BigInteger stripNum;


    /**
     *
     * 供应商
     * */
    private String supplier;


    /**
     * 检查时间
     *
     * */
    private String checkTime;


    /**
     *
     * */


    /**
     * 数量
     * */
    private BigInteger codeNum;


    /**
     * 检查日期
     * */
    private String checkDate;


    /**
     *检察员
     * */
    private String checkName;

    /**
     * 状态
     * 1:已入库
     * 2：生产中
     * 3：已退回
     * */
    private int status;

    /**
     * 抽检状态
     *
     * */
    private int checkStatus;


    /**
     * 抽检类型
     * */
    private BigInteger typeCode;

    /**
     * 抽检类型名
     * */
    private  String typeName;



    /***
     *
     * 抽检详情List
     *
     * */
    private List<CodeBatchDetails> codeBatchDetails;






}
