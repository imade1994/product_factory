package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:08
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class MaterialConsumeDetails implements Serializable {
    /**
     * 主键
     * */
    private String id;

    /**
     * 辅料消耗ID
     * */
    private String consumeId;

    /**
     * 箱编号
     * */
    private String boxCode;

    /**
     * 开始投料时间
     * */
    private String beginDate;

    /**
     * 结束投料时间
     * */
    private String endDate;

    /**
     * 辅料生产时间
     * */
    private String produceDate;

    /**
     *
     * 采购批次号
     * */
    private String purchaseNumber;

    /**
     * 辅料供应商
     * */
    private String supplier;

    /**
     * 备注
     * */
    private String remark;

}
