package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Throwing;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface IThrowingService {

    Throwing getThrowingDetails(Map map);

    Throwing getThrowByOrder(String throwingNumber);
}
