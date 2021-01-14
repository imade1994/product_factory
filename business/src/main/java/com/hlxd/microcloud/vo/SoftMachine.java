package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1416:03
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Data
public class SoftMachine implements Serializable {

    private String machineModel;


    private List<String> machineCodes;


}
