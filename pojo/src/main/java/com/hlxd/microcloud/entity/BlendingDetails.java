package com.hlxd.microcloud.entity;

import lombok.Data;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2116:57
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class BlendingDetails {
    /**
     * 主键
     * */
    private String id;
    /**
     * 投料批次
     * */
    private String blendingNumber;
    /**
     * 箱编号
     *
     * */
    private String boxCode;
    /**
     * 投料时间
     * */
    private String blendingDate;
    /**
     * 烟叶年份
     * */
    private String tobaccoYear;
    /**
     * 产地
     * */
    private String produceArea;
    /**
     * 品种
     * */
    private String breed;
    /**
     * 等级
     * */
    private String level;
    /**
     *
     * 毛重
     * */
    private String grossWeight;

    /**
     *
     * 净重
     * */
    private String netWeight;

    /**
     * 复烤批次号
     * */
    private String redryNumber;

    /**
     * 备注
     * */
    private String remark;
}
