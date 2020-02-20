package com.hlxd.microcloud.service.impl;


import com.hlxd.microcloud.dao.UserMapper;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.SysUser;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-02-19
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserInfo(String name, String password) {
        UserInfo userInfo = new UserInfo();
        SysUser sysUser = userMapper.getUserInfo(name,password);
        if(null != sysUser){
            userInfo.setSysUser(sysUser);
        }
        return userInfo;
    }

    @Override
    public SysUser getUser(String name, String password) {
        return userMapper.getUserInfo(name, password);
    }
}
