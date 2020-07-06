package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:33
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemRoleMenu implements Serializable {

    /**
     * id
     * */
    private String id;

    /**
     * 角色ID
     * */
    private String roleId;

    /**
     * 菜单ID
     * */
    private String menuId;

    /**
     * 方法ID
     * */
    private String methodId;

    /**
     * 启用状态
     * 0 禁用 1 启用
     * */
    private int status;
}
