package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.TimeBreak;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2817:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public interface TimeBreakService {

    /**
     * 查询详细停机记录
     * */
    List<TimeBreak> getTimeBreak(Map map);


    /**
     * 获取记录总数
     * */
    int TimeBreakCount(Map map);
}
