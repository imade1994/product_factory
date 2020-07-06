package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1817:02
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CodeBatch implements Serializable {


    /**
     * 批次号
     * */
    private String batchNo;


    /**
     * 牌号
     * */
    private String brandName;


    /**
     * 数量
     * */
    private int codeNum;


    /**
     * 检查日期
     * */
    private String checkDate;


    /**
     *检察员
     * */
    private String checkName;

    /**
     * 状态
     * 1:已入库
     * 2：生产中
     * 3：已退回
     * */
    private int status;

    /**
     * 表名
     * */
    private String tableName;





}
