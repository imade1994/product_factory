package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.IllegalCode;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/815:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
public interface IllegalCodeService {

    /**
     * 1
     * 重复关联
     * 包，条
     * */
    void getRepeatCode(String tableName,int packageType);


    /**
     * 2
     * 关联错误的烟条
     * 数量不正确
     * */
    void wrongRelationPackage(String tableName);


    /**
     * 3
     * 关联错误的件
     * 数量不正确
     * */
    void wrongRelationStrip(String tableName);


    /**
     * 4
     * 含废码的条
     *
     * */
    void containDiscardCodeStrip(String tableName);


    /**
     * 5
     * 含废码的件
     *
     * */
    void containDiscardCodeItem(String tableName);


    /**
     * 6
     * 标记剔除的件
     *
     * */
    void markedRejectItem(String tableName);



    /**
     * 获取异常吗分类统计数据
     * @param 以时间，机台，包装类型分类
     * */
    List<IllegalCode> getIllegalCodeCount(Map map);


    /**
     * 获取异常码详细码数据
     * @param produceDate
     * @param machineCode
     * @param packageType
     * @param illegalType
     * @param fromIndex
     * @param endIndex
     * */
    List<IllegalCode> getIllegalCode(Map map);

}
