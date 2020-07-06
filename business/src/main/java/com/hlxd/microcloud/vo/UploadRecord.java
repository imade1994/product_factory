package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2013:39
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class UploadRecord implements Serializable {

    /**
     * id
     * */
    private String id;

    /**
     *
     * 条包数量
     * */
    private int packTwig;

    /**
     * 条件数量
     * */
    private int twigItem;

    /**
     * 同步数据数量
     * */
    private int statistic;

    /**
     * 上传模式
     * 1:自动 2：手动
     * */
    private int uploadModel;

    /**
     * 完成时间
     * */
    private String completeDate;

    /**
     * 数据生产日期
     * */
    private String produceDate;

    /**
     * 同步人
     * */
    private String manualPeople;

    /**
     * 任务状态
     * */
    private int status;
}
