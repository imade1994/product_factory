package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:31
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemMenu implements Serializable {

    /**
     * id
     * */
    private String id;

    /**
     * 父菜单ID
     * */
    private String parentId;


    /**
     * 是否目录
     * */
    private int isFolder;

    /**
     * 菜单名
     * */
    private String menuName;

    /**
     * 路径
     * */
    private String path;

    /**
     * 组件名
     * */
    private String component;

    /**
     * 启用状态
     * 0 禁用 1 启用
     * */
    private int status;


    /**
     * 属性集合
     * */
    private MenuMeta extraProps;

    /**
     * 授权方法集合
     * */
    private List<String> permissions;

    /**
     * 子菜单
     * */
    private List<SystemMenu> routes;


}
