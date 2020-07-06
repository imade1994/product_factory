package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.SystemRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:21
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface SystemRoleService {

    /**
     * 新增系统角色
     * */
    void addSystemRole(SystemRole systemRole);

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
