package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.FeedingRecord;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:52
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface IFeedingService {


    List<FeedingRecord> getFeedingRecord(Map map);

    //根据喂丝机号和喂丝时间获取制丝号
    FeedingRecord selectByFeed(String feedTime,String feedingMachineCode);
}
