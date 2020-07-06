package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/3/316:06
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CodeCount implements Serializable {
    //統計時間
    private String time;
    //計數類型
    private String type;

    private String rejectType;
    //统计机台
    private String machineCode;
    //統計數量
    private String count_remark;

    //通过数量
    private String approval;

    //剔除数量
    private String reject;

    //总扫描量
    private String totalScan;

    //統計班次
    private int period;
    //统计日毫秒值
    private long date;
    ///
    private String remark;


}
