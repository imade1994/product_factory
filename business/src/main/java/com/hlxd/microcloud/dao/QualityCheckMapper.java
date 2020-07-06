package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.RandomCheckRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/310:39
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface QualityCheckMapper {
    /**
     * 质检记录查询
     * */
    List<RandomCheckRecord> getRandomCheckList(Map map);

}
