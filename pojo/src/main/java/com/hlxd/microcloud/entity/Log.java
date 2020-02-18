package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/12/1116:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class Log implements Serializable {

    private String id;


    private String faultTime;


    private String message;

}
