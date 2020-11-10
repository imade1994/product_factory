package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.RejectMapper;
import com.hlxd.microcloud.service.RejectService;
import com.hlxd.microcloud.vo.RejectDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2516:17
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class RejectServiceImpl implements RejectService {

    @Autowired
    private RejectMapper rejectMapper;


    @Override
    public List<RejectDetails> getRejectDetails(Map map) {
        return rejectMapper.getRejectDetails(map);
    }

    @Override
    public int RejectCount(Map map) {
        return rejectMapper.RejectCount(map);
    }
}
