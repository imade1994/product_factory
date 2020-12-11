package com.hlxd.microcloud.service;

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
}
