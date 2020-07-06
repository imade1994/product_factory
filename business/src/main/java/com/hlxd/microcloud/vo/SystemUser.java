package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:36
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemUser implements Serializable {
    /**
     * id
     * */
    private String id;


    /**
     * 用户名
     * */
    private String userName;

    /**
     * 密码
     * */
    private String passWord;

    /**
     * 部门
     * */
    private String department;

    /**
     * 创建人
     * */
    private String createPeople;

    /**
     * 过期时间
     * */
    private String checkDate;

    /**
     * 启用状态
     * */
    private int status;

    /**
     * 二级密码
     * */
    private String secondPassword;

    /**
     * 权限
     * */
    private List<SystemRole> systemRoles;






}
