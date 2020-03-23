package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/3/1214:33
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class Reject implements Serializable {


    //时间
    private String time;

    //设备
    private String machineCode;

    //剔除ID
    private String rejectId;
    //包装机剔除还是装箱机
    private int type;

    //剔除原因
    private int rejectType;

    //剔除备注
    private String remark;

}
