package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1316:46
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class SoftManagementRecordDetails implements Serializable {


    private int id;


    private int versionId;

    private String machineCode;


    private String machineName;

}
