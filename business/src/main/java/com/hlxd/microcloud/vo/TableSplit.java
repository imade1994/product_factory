package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/410:35
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class TableSplit implements Serializable {

    private String tableName;

    private String produceDate;

    private String machineCode;

    private String beginDate;

    private String endDate;


}
