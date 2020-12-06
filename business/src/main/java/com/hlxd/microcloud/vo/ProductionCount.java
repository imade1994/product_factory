package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1418:22
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class ProductionCount implements Serializable {

    /**
     * 件产量
     * */
    private int itemCount;



    /**
     * 包产量
     * */
    private int packageCount;


    /**
     * 条产量
     * */
    private int stripCount;


}
