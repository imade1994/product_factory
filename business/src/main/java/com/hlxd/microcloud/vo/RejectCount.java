package com.hlxd.microcloud.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1114:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RejectCount implements Serializable {
    /**
     *
     * */
    private int id;


    private float countRemark;


    private String machineCode;


    private int period;


    private String date;


    private int rejectType;


    private int type;


    private String remark;


    private String factoryName;
}
