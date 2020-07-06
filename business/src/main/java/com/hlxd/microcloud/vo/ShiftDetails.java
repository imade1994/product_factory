package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/314:47
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class ShiftDetails implements Serializable {


    /**
     * 组id
     * */
    private int id;


    /**
     * 班ID
     * */
    private int shiftId;

    /**
     * 组名
     * */
    private String className;

    /**
     * 开始时间
     * */
    private int beginDate;

    /**
     * 结束时间
     * */
    private int endDate;

}
