package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2511:23
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class AllMachineCount implements Serializable {


    /**
     *实时扫描数
     * */
    private int realtimeScan;

    /**
     *实时扫描剔除数
     * */
    private int realtimeScanReject;

    /**
     *实时关联数
     * */
    private int realtimeRelate;

    /**
     *实时关联剔除数
     * */
    private int realtimeReject;
    /**
     * 小包剔除统计
     * */
    private String packageRejectCount;

    /**
     * 条包剔除统计
     * */
    private String stripRejectCount;

    /**
     * 累计抽检次数
     * */
    private String randomCheckCount;


    /**
     * 累计抽检失败数
     * */
    private String randomCheckFailCount;


    /**
     * 累计停机次数
     * */
    private String breakDownCount;


    /**
     * 累计停机时长
     * */
    private String breakTimeCount;

}
