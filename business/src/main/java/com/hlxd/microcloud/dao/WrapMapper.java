package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.WrapOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 卷包
 * @Author taojun
 * @Date 2019/11/2116:36
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface WrapMapper {

    WrapOrder getWrapOrder(Map map);

    List<WrapOrder> selectByWrapOrder(@Param("machineCode") String machineCode, @Param("produceDate") String produceDate, @Param("classId") String classId);

    WrapOrder selectByNumber(@Param("wrapsNumber") String wrapsNumber);
}
