package com.hlxd.microcloud.vo;

import lombok.Data;

import java.math.BigInteger;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/2314:05
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CountScan {




/**********---------------------- 读码率------------------********************************************/
    /**
     * 数采小包产量
     * */
    private BigInteger collectionPackage;


    /**
     * 二维码扫描数量
     * */
    private BigInteger packCount;


    /**
     * 成功读码数量
     * */
    private BigInteger packSuccess;

    /**
     * 小包读码率
     * */
    private float packRate;
    /**********----------------------关联率------------------********************************************/

    /**
     * 数采条盒产量
     * */
    private BigInteger collectionStrip;


    /**
     * 条盒关联产量
     * */
    private BigInteger stripCount;


    /**
     * 成功读码数量
     * */
    private BigInteger stripSuccess;

    /**
     * 小包读码率
     * */
    private float stripRate;
    /**********----------------------作业率------------------********************************************/


    /**
     * 停机总次数
     * */
    private int breakCount;


    /**
     * 停机总时长
     * */
    private int breakTimeCount;

    /**
     * 系统运行时长
     * */
    private int systemTime;


    /**
     * 关联作业率
     * */
    private float workRate;

    /**********----------------------剔除分析------------------********************************************/

    /**
     * 类型代码
     * */
    private int typeCode;

    /**
     * 剔除类型代码
     * */
    private int rejectTypeCode;


    /**
     * 剔除原因
     * */
    private String rejectReason;

    /**
     * 剔除总量
     * */
    private int rejectCount;
    /**********----------------------码段使用------------------********************************************/

    /**
     * 小包扫描量
     * */
    private BigInteger packageScanCount;

    /**
     * 小包废码数量
     * */
    private BigInteger packageDiscardCount;


    /**
     * 条盒废码量
     * */
    private BigInteger stripDiscardCount;

    /**
     * 条盒扫描量
     * */
    private BigInteger stripScanCount;

    /**
     * 成功关联烟条数
     * */
    private BigInteger stripSuccessRelateCount;

    /**
     * 解除关联烟条数
     * */
    private BigInteger stripDisCardRelateCount;



    /**
     * 机台
     * */
    private String machineCode;

    /**
     * 机台名
     * */
    private String machineName;


    /**
     * 品牌
     * */
    private String brandName;

    /**
     * 班组名
     * */
    private String shiftName;

    /**
     * 生产日期
     * */
    private String productDate;


    /**
     * 二维码
     * */
    private String qrCode;


    /**
     * 重复次数
     * */
    private int repeatCount;





}
