package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SystemUser;
import org.apache.ibatis.annotations.Mapper;

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




}
