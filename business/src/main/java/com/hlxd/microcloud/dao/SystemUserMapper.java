package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/1015:11
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface SystemUserMapper {

    /**
     * 用户登录接口
     * */
    List<SystemUser> login(Map map);


    /**
     * 新增用户
     * */
    void addUser(@Param("vo") SystemUser systemUser);



    /**
     * 删除用户
     *
     * */
    void deleteUser(@Param("id")String id);


    /**
     * 更新用户信息
     * */
    void updateUser(Map map);

    /**
     * 获取所有用户
     * */
    List<SystemUser> getAllUser(Map map);

    /**
     * 删除用户菜单
     * */
    void deleteUserMenu(Map map);

    /**
     * 新增用户菜单
     * */
    void addUserMenu(Map map);

    /**
     * 获取用户授权菜单列表
     * */
    List<SystemMenu> getUserMenu(Map map);




}
