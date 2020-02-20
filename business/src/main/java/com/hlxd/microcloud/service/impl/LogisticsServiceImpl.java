package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.LogisticsMapper;
import com.hlxd.microcloud.entity.Logistics;
import com.hlxd.microcloud.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:58
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class LogisticsServiceImpl implements ILogisticsService {
    @Autowired
    private LogisticsMapper logisticsMapper;


    @Override
    public List<Logistics> getLogistics(Map map) {
        return logisticsMapper.getLogistics(map);
    }
}
