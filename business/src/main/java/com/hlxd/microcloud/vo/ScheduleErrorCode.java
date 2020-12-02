package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/115:35
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class ScheduleErrorCode implements Serializable {


    private String qrCode;

    private int id;

    private String scheduleDate;

    private String relationDate;

    private String machineCode;

    private String tableName;

    private int executeState;
}
