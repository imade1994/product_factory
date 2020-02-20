package com.hlxd.microcloud.service;


import com.hlxd.microcloud.entity.*;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-19
 */
public interface UserService {


    UserInfo getUserInfo(String name, String password);
    SysUser getUser(String name, String password);

}
