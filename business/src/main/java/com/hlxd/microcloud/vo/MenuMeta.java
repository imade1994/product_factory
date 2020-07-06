package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2617:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class MenuMeta implements Serializable {

    /**
     * id
     * */
    private String id;


    /**
     * menuId
     * */
    private String menuId;

    /**
     * 标题
     * */
    private String title;

    /**
     * 转向
     * */
    private String to;


    /**
     * 未知
     * */
    private String exact;





}
