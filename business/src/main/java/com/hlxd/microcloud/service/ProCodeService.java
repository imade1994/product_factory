package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.ProductionCount;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1317:44
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface ProCodeService {

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
     * 查询统计产量 包 条
     *
     * */
    ProductionCount getProductionByPeriod(Map map);

    /**
     * 查询统计产量  箱
     *
     * */
    ProductionCount getItemProductionByPeriod(Map map);






    /**
     * 校验码是否存在于选定时间段内
     * */
    int validateCode(Map map);


}
