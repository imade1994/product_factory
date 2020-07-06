package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/1015:24
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface SystemUserService {




    /**
     * 用户登录服务
     * */
    List<SystemUser> login(Map map);



}
