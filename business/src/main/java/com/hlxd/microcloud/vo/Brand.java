package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/317:50
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class Brand implements Serializable {
    /**
     * id
     * */
    private int id;


    /**
     * 品牌名
     * */
    private String brandName;

    /**
     *包一维条码
     * */
    private String packageBarcode;


    /**
     * 条一维条码
     * */
    private String stripBarcode;

    /**
     * 件一条码
     * */
    private String itemBarcode;

    /**
     * 盒支数
     * */
    private int cigarettes;

    /**
     * 条包数
     * */
    private int packages;

    /**
     * 件条数
     * */
    private int strips;

}
