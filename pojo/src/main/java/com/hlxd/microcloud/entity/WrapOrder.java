package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:50
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class WrapOrder implements Serializable {
    /**
     * 主键
     * */
    private String id;

    /**
     * 卷包工单号
     * */
    private String wrapsNumber;

    /**
     * 设备编号
     * */
    private String machineCode;


    private String machineName;

    /**
     * 生产时间
     * */
    private String produceDate;
    /**
     * 班次ID
     * */
    private String classId;
    /**
     * 班次名
     * */
    private String className;
    /**
     * 牌号
     *
     * */
    private String brandCode;

    private String brandName;
    /**
     *
     * 计划开始时间
     * */
    private String planBeginDate;

    /**
     * 计划结束时间
     *
     * */
    private String planEndDate;
    /**
     *
     * 计划产量
     * */
    private String planQuantity;

    /**
     * 实际开始时间
     * */
    private String doBeginDate;

    /**
     * 实际结束时间
     * */
    private String doEndDate;

    /**
     * 实际产量
     * */
    private String doQuantity;

    /**
     * 备注
     * */
    private String remark;
    /**
     * 卷包质检数据
     * 0:自检,1:抽检,2:巡检
     * */
    private List<Verification> Verifications;
    /**
     * 物料消耗数据
     *
     * */
    private List<MaterialConsume> materialConsumes;







}
