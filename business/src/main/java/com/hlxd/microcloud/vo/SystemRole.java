package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:27
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemRole implements Serializable {
    /**
     * id
     * */
    private String id;


    /**
     * 角色名
     * */
    private String roleName;

    /**
     * 部门
     * */
    private String department;


    /**
     * 创建人
     * */
    private String createPeople;

    /**
     * 启用状态
     * 0 禁用  1 启用
     * */
    private int status;

    /**
     * 授权菜单
     * */
    private List<SystemMenu> systemMenuList;


}
