package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.RandomCheckRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/310:53
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface QualityCheckService {


    /**
     * 质检记录查询
     * */
    List<RandomCheckRecord> getRandomCheckList(Map map);

}
