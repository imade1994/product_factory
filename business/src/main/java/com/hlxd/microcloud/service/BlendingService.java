package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Blending;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:52
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public interface BlendingService {

    List<Blending> getBlending(Map map);

}
