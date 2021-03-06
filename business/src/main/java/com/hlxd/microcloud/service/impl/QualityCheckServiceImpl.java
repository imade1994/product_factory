package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.QualityCheckMapper;
import com.hlxd.microcloud.service.QualityCheckService;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.RandomCheckDetails;
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

    @Override
    public ProCode getCodeDetail(Map map) {
        return qualityCheckMapper.getCodeDetail(map);
    }

    @Override
    public void insertRandomCheck(RandomCheckRecord randomCheckRecord) {
        qualityCheckMapper.insertRandomCheck(randomCheckRecord);

    }

    @Override
    public void insertRandomCheckDetails(List<RandomCheckDetails> randomCheckDetails) {
        qualityCheckMapper.insertRandomCheckDetails(randomCheckDetails);

    }

    @Override
    public ProCode getCodeRelation(String qrCode) {
        return qualityCheckMapper.getCodeRelation(qrCode);
    }

    @Override
    public List<ProCode> getCodeList(Map map) {
        return qualityCheckMapper.getCodeList(map);
    }
}
