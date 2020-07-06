package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/117:04
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class Husband implements Serializable {

    /**
     * 身高
     * */
    private int height;

    /**
     * 体重
     * */
    private int weight;

    /**
     * 脸型
     * */
    private String feature;


    /**
     * 收入
     * (单位元)
     * */
    private int income;


    /**
     * 特长
     * */
    private List<String> specialities;

    /**
     *肤色
     * */
    private String skinColour;

    /**
     * 职业
     * */
    private String professional;







}
