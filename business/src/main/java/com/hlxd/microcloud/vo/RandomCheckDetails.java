package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/216:57
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RandomCheckDetails implements Serializable {


    /**
     * id
     *
     * */
    private int id;

    /**
     *
     * 抽检Id
     * */
    private int randomCheckId;

    /**
     * 包-件码
     * */
    private String qrCode;
}
