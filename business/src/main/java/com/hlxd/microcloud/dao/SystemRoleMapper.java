package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SystemRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2514:49
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface SystemRoleMapper {
    /////////////////////角色相关接口/////////////////////////////////
    /**
     * 新增系统角色
     * */
    void addSystemRole(@Param("vo") SystemRole systemRole);

    /**
     * 删除系统角色
     * */
    void deleteSystemRole(String roleId);

    /**
     * 更改系统角色
     * */
    void updateSystemRole(Map map);

    /**
     * 查询系统角色
     * */
    List<SystemRole> getSystemRole();


    /**
     * 查询角色授权信息
     * */
    List<SystemRole> getAuthorizationRole(Map map);

}
