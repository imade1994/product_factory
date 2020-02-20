package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.FeedingMapper;
import com.hlxd.microcloud.entity.FeedingRecord;
import com.hlxd.microcloud.service.IFeedingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:57
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class FeedingServiceImpl implements IFeedingService {

    @Autowired(required = false)
    private FeedingMapper feedingMapper;

    @Override
    public List<FeedingRecord> getFeedingRecord(Map map) {
        return feedingMapper.getFeedingRecord(map);
    }

    @Override
    public FeedingRecord selectByFeed(String feedTime, String feedingMachineCode) {
        return feedingMapper.selectByFeed(feedTime,feedingMachineCode);
    }
}
