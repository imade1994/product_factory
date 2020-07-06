package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SystemDict;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemRole;
import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/259:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface SystemDictMapper {

    /////////////////////数据字典相关接口/////////////////////////////////
    /**
     * 查询数据字典 条件查询
     * */
    List<SystemDict> getDictList(Map map);

    /**
     * 查询数据字典 全量查询
     * */
    List<SystemDict> getAllDictList(Map map);

    /**
     * 新增数据字典
     * */
    void insertSystemDict(@Param("vo") SystemDict systemDict);

    /**
     * 更新数据字典
     * */
    void updateSystemDict(@Param("vo")SystemDict systemDict);

    /**
     * 移除数据字典
     * */
    void deleteSystemDict(Map map);

    /**
     * 删除校验
     *
     * */
    int countValidate(Map map);















}
