package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.ProductionCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1317:42
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface ProCodeMapper {

    /**
     * 查询编码
     * */
    ProCode getProCode(Map map);


    /**
     *
     * 获取单个 编码
     * */
    ProCode getSingleProCode(Map map);

    /**
     * 查询统计产量
     *
     * */
     ProductionCount getProductionByPeriod(Map map);


     /**
      * 校验码是否存在于选定时间段内
      * */
     int validateCode(Map map);




}
