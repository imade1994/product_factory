package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.CancelRelation;
import com.hlxd.microcloud.vo.DiscardCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1515:31
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface DiscardService {


    /**
     * 查询废码记录
     * */
    List<DiscardCode> getDiscardCodeList(Map map);



    /**
     * 关系解除记录
     * */
    List<CancelRelation> getCancelRelationList(Map map);



}
