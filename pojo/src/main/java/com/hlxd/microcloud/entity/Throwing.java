package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Throwing implements Serializable {
    /**
     * 主键
     * */
    private String id;

    /**
     * 制丝工单号
     * */
    private String throwingNumber;

    /**
     * 计划开始时间
     * */
    private String planBeginDate;

    /**
     * 计划结束时间
     * */
    private String planEndDate;

    /**
     * 实际开始时间
     * */
        private String doBeginDate;

    /**
     * 实际结束时间
     * */
    private String doEndDate;

    /**
     * 投料重量
     * */
    private String blendingWeight;

    /**
     * 产品重量
     * */
    private String productWeight;

    /**
     * 产线ID
     * */
    private String lineCode;
    /**
     * 产线名
     * */
    private String lineName;
    /**
     * 工段ID
     * */
    private String sectionCode;
    /**
     * 工段名
     * */
    private String sectionName;
    /**
     * 班组ID
     * */
    private String classId;
    /**
     * 班组名
     * */
    private String className;
    /**
     * 投料批次
     * */
    private String blendingNumber;
    /**
     * 备注
     * */
    private String remark;

    /**
     * 制丝质检数据
     * 0:自检,1:抽检,2:巡检
     * */
    private List<Verification> Verifications;
    /**
     * 物料消耗数据
     *
     * */
    private List<MaterialConsume> materialConsumes;

    /**
     * 投料信息
     * */
    private List<Blending> blendings;


}
