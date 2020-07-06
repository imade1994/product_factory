package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.QualityCheckMapper;
import com.hlxd.microcloud.service.QualityCheckService;
import com.hlxd.microcloud.vo.RandomCheckRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/310:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class QualityCheckServiceImpl implements QualityCheckService {


    @Autowired
    private QualityCheckMapper qualityCheckMapper;


    @Override
    public List<RandomCheckRecord> getRandomCheckList(Map map) {
        return qualityCheckMapper.getRandomCheckList(map);
    }
}
