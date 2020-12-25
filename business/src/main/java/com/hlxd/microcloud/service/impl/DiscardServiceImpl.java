package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.DiscardMapper;
import com.hlxd.microcloud.service.DiscardService;
import com.hlxd.microcloud.vo.CancelRelation;
import com.hlxd.microcloud.vo.DiscardCode;
import com.hlxd.microcloud.vo.DiscardCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1515:32
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class DiscardServiceImpl implements DiscardService {
    @Autowired
    DiscardMapper discardMapper;

    @Override
    public List<DiscardCode> getDiscardCodeList(Map map) {
        return discardMapper.getDiscardCodeList(map);
    }

    @Override
    public List<DiscardCount> getDisCardCount(Map map) {
        return discardMapper.getDisCardCount(map);
    }

    @Override
    public List<CancelRelation> getCancelRelationList(Map map) {
        return discardMapper.getCancelRelationList(map);
    }


    @Override
    public List<DiscardCount> getDisCardCountList(Map map) {
        return discardMapper.getDisCardCountList(map);
    }

    @Override
    public void updateDiscardCodeUpload(Map map) {
        discardMapper.updateDiscardCodeUpload(map);
    }

    @Override
    public void insertDiscardCodeRecord(DiscardCount discardCount) {
        discardMapper.insertDiscardCodeRecord(discardCount);
    }
}
