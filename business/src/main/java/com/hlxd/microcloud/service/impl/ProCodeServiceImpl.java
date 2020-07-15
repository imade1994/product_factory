package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.ProCodeMapper;
import com.hlxd.microcloud.service.ProCodeService;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.ProductionCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1317:45
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class ProCodeServiceImpl implements ProCodeService {

    @Autowired
    private ProCodeMapper proCodeMapper;


    @Override
    public ProCode getProCode(Map map) {
        return proCodeMapper.getProCode(map);
    }

    @Override
    public ProCode getSingleProCode(Map map) {
        return proCodeMapper.getSingleProCode(map);
    }

    @Override
    public ProductionCount getProductionByPeriod(Map map) {
        return proCodeMapper.getProductionByPeriod(map);
    }

    @Override
    public int validateCode(Map map) {
        return proCodeMapper.validateCode(map);
    }
}
