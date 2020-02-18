package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2116:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Blending implements Serializable {

    private String id;

    /**
     * 投料日期
     * */
    private String blendingDate;
    /**
     * 投料批次
     *
     * */
    private String blendingNumber;
    /**
     * 生产线code
     * */
    private String lineCode;
    /**
     *
     * 生产线名
     * */
    private String lineName;
    /**
     *
     * 品牌编码
     * */
    private String brandCode;
    /**
     * 品牌名字
     *
     * */
    private String brandName;
    /**
     *
     * 叶组配方
     * */
    private String blendingFormulation;
    /**
     *
     * 计划投料数量
     * */
    private String planBlendingQuantity;
    /**
     * 计划投料重量
     *
     * */
    private String planBlendingWeight;
    /**
     * 实际投料数量
     *
     * */
    private String doBlendingQuantity;
    /**
     * 实际投料重量
     * */
    private String doBlendingWeight;
    /**
     * 投料状态
     * */
    private String blendingStatus;
    /**
     * 备注
     * */
    private String remark;

    /**
     * 投料详情
     * */
    private List<BlendingDetails> blendingDetails;
}
