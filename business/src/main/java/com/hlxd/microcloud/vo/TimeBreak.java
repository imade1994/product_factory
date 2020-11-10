package com.hlxd.microcloud.vo;

import lombok.Data;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2817:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class TimeBreak {





    /**
     * 机台号
     * */
    private String machineName;


    /**
     * 停机开始时间
     * */
    private String beginDate;

    /**
     * 停机结束时间
     * */
    private String endDate;

    /**
     * 停机时长 s
     * */
    private int timeBreakSecond;

    /**
     * 停机原因
     * */
    private String breakReason;
}
