package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.RejectDetails;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2516:16
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public interface RejectService {



    List<RejectDetails> getRejectDetails(Map map);

    /**
     * 获取记录总数
     * */
    int RejectCount(Map map);
}
