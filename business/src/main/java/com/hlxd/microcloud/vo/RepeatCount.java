package com.hlxd.microcloud.vo;

import lombok.Data;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/1415:06
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class RepeatCount  {

    /**
     * 二维码
     * */
    private String qrCode;


    /**
     * 重复次数
     * */
    private int repeatCount;

}
