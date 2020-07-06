package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:41
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemUserRole implements Serializable {
    /**
     * id
     * */
    private String id;

    /**
     * 角色ID
     * */
    private String roleId;

    /**
     * 用户ID
     * */
    private String userId;

    /**
     * 启用状态
     * 0 禁用 1 启用
     * */
    private int status;




}
