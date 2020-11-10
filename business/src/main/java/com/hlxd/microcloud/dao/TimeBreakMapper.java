package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.TimeBreak;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2817:22
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface TimeBreakMapper {

    /**
     * 查询详细停机记录
     * */
    List <TimeBreak> getTimeBreak(Map map);

    /**
     * 获取记录总数
     * */
    int TimeBreakCount(Map map);
}
