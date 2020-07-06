package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemUserMapper;
import com.hlxd.microcloud.service.SystemUserService;
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
}
