package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/216:42
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RandomCheckRecord implements Serializable {


    /**
     * id
     * */
    private int id;

    /**
     *
     * 开始抽检时间
     * */
    private String beginCheckDate;

    /**
     * 抽检结束时间
     *
     * */
    private String endCheckDate;

    /**
     * 机台号
     *
     * */
    private String machineCode;

    /**
     * 抽检类型
     * 1：条 2：件
     * */
    private int checkType;

    /**
     * 质检类型
     * 0:机台自检 1：工艺巡检  2：关联抽检
     * */
    private int qualityType;


    /**
     * 条件码
     * */
    private String qrCode;


    /**
     * 抽检结果
     * */
    private int checkStatus;


    /**
     * 在线关联抽检
     * */
    private int onLineCheck;

    /**
     * 抽检包，条码列表
     *
     * */
    private List<RandomCheckDetails> randomCheckDetails;


}
