package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/314:43
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class Shift implements Serializable {
    /**
     * id
     * */
    private int id;


    /**
     * 班组名
     * */
    private String shiftName;


    /**
     * 状态
     * */
    private int status;

    /**
     * 班组详情
     * */
    private List<ShiftDetails> shiftDetails;





}
