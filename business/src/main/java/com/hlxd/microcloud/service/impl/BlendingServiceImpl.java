package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.BlendingMapper;
import com.hlxd.microcloud.entity.Blending;
import com.hlxd.microcloud.service.BlendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:56
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class BlendingServiceImpl implements BlendingService {
    @Autowired
    private BlendingMapper blendingMapper;

    @Override
    public List<Blending> getBlending(Map map) {
        return blendingMapper.getBlending(map);
    }
}
