package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemRoleMapper;
import com.hlxd.microcloud.service.SystemRoleService;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:22
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {
    @Autowired
    SystemRoleMapper systemRoleMapper;



    @Override
    public void addSystemRole(SystemRole systemRole) {
        systemRoleMapper.addSystemRole(systemRole);
    }

    @Override
    public void deleteSystemRole(String department_id) {
        systemRoleMapper.deleteSystemRole(department_id);
    }

    @Override
    public void updateSystemRole(SystemRole systemRole) {
        systemRoleMapper.updateSystemRole(systemRole);
    }

    @Override
    public List<SystemRole> getSystemRole() {
        return systemRoleMapper.getSystemRole();
    }

    @Override
    public List<SystemRole> getAuthorizationRole(Map map) {
        return systemRoleMapper.getAuthorizationRole(map);
    }

    @Override
    public List<SystemMenu> getDepartmentMenu(Map map) {
        return systemRoleMapper.getDepartmentMenu(map);
    }
}
