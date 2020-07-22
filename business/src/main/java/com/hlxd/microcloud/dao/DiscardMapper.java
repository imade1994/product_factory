package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.CancelRelation;
import com.hlxd.microcloud.vo.DiscardCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1515:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface DiscardMapper {

    /**
     * 查询废码记录
     * */
    List<DiscardCode> getDiscardCodeList(Map map);


    /**
     * 关系解除记录
     * */
    List<CancelRelation> getCancelRelationList(Map map);







}
