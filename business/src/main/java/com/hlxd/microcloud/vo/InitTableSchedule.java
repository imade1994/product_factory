package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/2510:23
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class InitTableSchedule implements Serializable {

    private String machineCode;

    private String machineName;

    private String beginDate;

    private String endDate;

    private String dateString;

}
