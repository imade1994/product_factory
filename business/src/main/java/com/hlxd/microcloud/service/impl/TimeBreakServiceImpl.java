package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.TimeBreakMapper;
import com.hlxd.microcloud.service.TimeBreakService;
import com.hlxd.microcloud.vo.TimeBreak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class TimeBreakServiceImpl implements TimeBreakService {

    @Autowired
    private TimeBreakMapper timeBreakMapper;


    @Override
    public List<TimeBreak> getTimeBreak(Map map) {
        return timeBreakMapper.getTimeBreak(map);
    }

    @Override
    public int TimeBreakCount(Map map) {
        return timeBreakMapper.TimeBreakCount(map);
    }
}
