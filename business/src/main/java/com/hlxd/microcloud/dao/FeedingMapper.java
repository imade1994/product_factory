package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.FeedingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 喂丝
 * @Author taojun
 * @Date 2019/11/2116:37
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface FeedingMapper {

    List<FeedingRecord> getFeedingRecord(Map map);

    //根据供丝机号和喂丝时间获取制丝号
    FeedingRecord selectByFeed(@Param("feedTime") String feedTime, @Param("feedingMachineCode")String feedingMachineCode);
}
