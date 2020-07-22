package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1614:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class BasicEquipment implements Serializable {

    /**
     * id
     * */
    private BigInteger id;



    /**
     *  设备序列号
     * */
    private String equipmentSeriesNo;


    /**
     * 设备编码
     * */
    private String equipmentCode;


    /**
     * 设备类型
     * */
    private BigInteger equipmentType;

    /**
     * 设备名称
     * */
    private String equipmentName;


    /**
     *
     * 设备型号
     * */
    private String equipmentModel;


    /**
     * 采购日期
     * */
    private String purchaseDate;


    /**
     * 工作状态
     * */
    private int workStatus;


    /**
     * 安装位置
     * */
    private String relationMachine;


    /**
     * 合同号
     * */
    private String contractNo;

    /**
     * 备注
     * */
    private String remark;


}
