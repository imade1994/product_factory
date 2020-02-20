package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 物流
 * @Author taojun
 * @Date 2019/11/2116:37
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface LogisticsMapper {

    List<Logistics> getLogistics(Map map);
}
