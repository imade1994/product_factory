package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2214:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Logistics implements Serializable {

    /**
     * 主键
     * */
    private String id;

    /**
     * 一号工程码
     * */
    private String qrCode;

    /**
     * 入库时间
     * */
    private String inHouseDate;

    /**
     * 出库时间
     * */
    private String outHouseDate;

    /**
     * 托盘ID
     * */
    private String palletId;

    /**
     * 车牌号
     * */
    private String carNumber;

    /**
     * 提货单号
     * */
    private String pickNumber;

    /**
     * 合同号
     * */
    private String contractNumber;

    /**
     * 收货商公司代码
     * */
    private String companyCode;

    /**
     * 收货商业公司名称
     *
     * */
    private String companyName;

    /**
     * 到达时间
     * */
    private String arrivalDate;

    /**
     * 验证状态
     * */
    private String verifyStatus;

    /**
     * 备注
     * */
    private String remark;


}
