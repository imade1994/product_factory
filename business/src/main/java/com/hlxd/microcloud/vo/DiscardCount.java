package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2310:19
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class DiscardCount implements Serializable {

    private String countDate;


    private int packageCount;


    private int stripCount;


    private int uploadState;

    private String updateDate;

    private int uploadModel;





}
