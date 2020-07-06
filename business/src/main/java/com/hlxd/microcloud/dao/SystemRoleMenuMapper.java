package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2514:50
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface SystemRoleMenuMapper {

    /////////////////////角色菜单相关接口/////////////////////////////////

    /**
     * 新增角色菜单
     * */
    void addSystemRoleMenu(List<SystemRoleMenu> systemRoleMenus);


    /**
     *
     * 批量删除指定
     * */
    void deleteSystemRoleMenu(List<String> ids);

    /**
     * 删除角色菜单信息
     * */
    void deleteSingleSystemRoleMenu(@Param("roleId")String roleId);

    /**
     *查询角色菜单信息
     * */
    List<SystemRoleMenu> getSystemRoleMenu(Map map);



}
