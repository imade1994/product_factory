package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.ThrowingMapper;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.service.IThrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2914:00
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class ThrowingServiceImpl implements IThrowingService {
    @Autowired
    private ThrowingMapper throwingMapper;

    @Override
    public Throwing getThrowingDetails(Map map) {
        return throwingMapper.getThrowDetails(map);
    }

    @Override
    public Throwing getThrowByOrder(String throwingNumber) {
        return throwingMapper.getThrowByOrder(throwingNumber);
    }
}
