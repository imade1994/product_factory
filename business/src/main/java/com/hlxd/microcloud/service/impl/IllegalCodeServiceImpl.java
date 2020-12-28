package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.IllegalCodeMapper;
import com.hlxd.microcloud.service.IllegalCodeService;
import com.hlxd.microcloud.vo.IllegalCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/815:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Service
public class IllegalCodeServiceImpl implements IllegalCodeService {
    @Autowired
    IllegalCodeMapper illegalCodeMapper;


    @Override
    public void getRepeatCode(String tableName,int packageType) {
        illegalCodeMapper.getRepeatCode(tableName,packageType);
    }

    @Override
    public void wrongRelationPackage(String tableName) {
        illegalCodeMapper.wrongRelationPackage(tableName);

    }

    @Override
    public void wrongRelationStrip(String tableName) {
        illegalCodeMapper.wrongRelationStrip(tableName);

    }

    @Override
    public void containDiscardCodeStrip(String tableName) {
        illegalCodeMapper.containDiscardCodeStrip(tableName);

    }

    @Override
    public void containDiscardCodeItem(String tableName) {
        illegalCodeMapper.containDiscardCodeItem(tableName);

    }

    @Override
    public void markedRejectItem(String tableName) {
        illegalCodeMapper.markedRejectItem(tableName);
    }

    @Override
    public List<IllegalCode> getIllegalCodeCount(Map map) {
        return illegalCodeMapper.getIllegalCodeCount(map);
    }

    @Override
    public List<IllegalCode> getIllegalCode(Map map) {
        return illegalCodeMapper.getIllegalCode(map);
    }
}
