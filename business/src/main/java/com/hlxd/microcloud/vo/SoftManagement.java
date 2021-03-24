package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1210:44
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class SoftManagement implements Serializable {

    private int id;

    private int softId;

    private String softName;

    private String softRemark;

    private String lastVersion;

    private String softUploadTime;

    private String filePath;

    private String fileName;






}
