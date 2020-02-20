package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Logistics;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:53
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */

public interface ILogisticsService {

    List<Logistics> getLogistics(Map map);
}
