package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:46
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class VerificationDetails implements Serializable {
    /**
     * 主键
     * */
    private String id;
    /**
     * 检验单号
     * */
    private String verificationId;

    /**
     * 检验项代码
     * */
    private String itemCode;

    private String itemName;

    /**
     * 样本平均值
     * */
    private String average;

    private String standValue;
    /**
     * 样本最大值
     *
     * */
    private String simpleMax;
    /**
     * 样本最小值
     * */
    private String simpleMin;
    /**
     * CPK
     * */
    private String cpk;
    /**
     * cv
     * */
    private String cv;
    /**
     * 合格率
     * */
    private String matchRate;
    /**
     * 备注
     * */
    private String remark;


}
