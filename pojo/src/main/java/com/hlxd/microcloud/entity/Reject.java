package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:12
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Reject implements Serializable {
    /**
     *主键
     * */
    private String id;

    /**
     *
     * 剔除时间
     * */
    private String rejectDate;

    /**
     * 剔除类型
     *
     * */
    private String rejectType;

    /**
     * 剔除原因
     * */
    private String rejectReason;

    /**
     * 设备编号
     * */
    private String machineCode;

    /**
     * 设备名称
     * */
    private String machineName;

    /**
     * 备注
     * */
    private String remark;
}
