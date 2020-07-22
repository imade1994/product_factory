package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemRoleMenuMapper;
import com.hlxd.microcloud.service.SystemRoleMenuService;
import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:36
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class SystemRoleMenuServiceImpl implements SystemRoleMenuService {
    @Autowired
    SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public void addSystemRoleMenu(SystemRoleMenu systemRoleMenus) {
        systemRoleMenuMapper.addSystemRoleMenu(systemRoleMenus);
    }

    @Override
    public void deleteSystemRoleMenu(Map map) {
        systemRoleMenuMapper.deleteSystemRoleMenu(map);
    }

    @Override
    public void deleteSingleSystemRoleMenu(String department_id) {
        systemRoleMenuMapper.deleteSingleSystemRoleMenu(department_id);
    }

    @Override
    public List<SystemRoleMenu> getSystemRoleMenu(Map map) {
        return systemRoleMenuMapper.getSystemRoleMenu(map);
    }
}
