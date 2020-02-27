package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/1310:45
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT qrCodeExcute
 */
@Data
public class Qrcode implements Serializable {

    private String id;

    private String qrCode;

    private String parentCode;

    private String machineCode;

    private String brandName;

    private String factoryName;

    private String productTime;

    private String remark;

    private String type;

    private List<Qrcode> childrenList;
}
