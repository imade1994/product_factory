package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/2810:20
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class SoftVo implements Serializable {

    private int id;


    private String softName;


    private String matchMachineType;


    private String matchMachineName;


    private String softRemark;

    private List<SoftManagement> softVersions;

}
