package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.RejectDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2514:37
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface RejectMapper {


    /**
     * 获取剔除详情
     *
     *
     * */
    List<RejectDetails> getRejectDetails(Map map);

    /**
     * 获取记录总数
     * */
    int RejectCount(Map map);





}
