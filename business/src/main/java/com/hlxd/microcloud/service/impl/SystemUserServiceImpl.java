package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemUserMapper;
import com.hlxd.microcloud.service.SystemUserService;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/1015:27
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    SystemUserMapper systemUserMapper;


    @Override
    public List<SystemUser> login(Map map) {
        return systemUserMapper.login(map);
    }

    @Override
    public void addUser(SystemUser systemUser) {
        systemUserMapper.addUser(systemUser);
    }

    @Override
    public void deleteUser(String id) {
        systemUserMapper.deleteUser(id);
    }

    @Override
    public void updateUser(Map map) {
        systemUserMapper.updateUser(map);
    }

    @Override
    public List<SystemUser> getAllUser(Map map) {
        return systemUserMapper.getAllUser(map);
    }

    @Override
    public void deleteUserMenu(Map map) {
        systemUserMapper.deleteUserMenu(map);
    }

    @Override
    public void addUserMenu(Map map) {
        systemUserMapper.addUserMenu(map);

    }

    @Override
    public List<SystemMenu> getUserMenu(Map map) {
        return systemUserMapper.getUserMenu(map);
    }
}
