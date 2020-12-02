package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/111:07
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class InitTable  implements Serializable {

    private int id;

    private String tableName;

    /*格式    12121212*/
    private String produceDate;

    private String produceMachineCode;

    private String createDate;

    private String scheduleBeginDate;

    private String scheduleEndDate;
}
