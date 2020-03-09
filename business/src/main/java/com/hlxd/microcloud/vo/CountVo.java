package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/3/914:38
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class CountVo implements Serializable {
    //返回统计时间
    private String time;

    ///count(distinct)返回
    private String count;

    ///count(*)返回
    private String count_remark;


}
