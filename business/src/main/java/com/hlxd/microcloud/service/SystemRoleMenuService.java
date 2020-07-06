package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface SystemRoleMenuService {
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
    void deleteSingleSystemRoleMenu(String roleId);

    /**
     *查询角色菜单信息
     * */
    List<SystemRoleMenu> getSystemRoleMenu(Map map);
}
