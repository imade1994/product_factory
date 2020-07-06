package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.MenuMeta;
import com.hlxd.microcloud.vo.SystemMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2514:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface SystemMenuMapper {


    /////////////////////菜单相关接口/////////////////////////////////
    /**
     * 更改菜单标题
     * */
    void updateSystemMenu(Map map);

    /**
     * 启用或关闭菜单
     * */
    void changeStatus(Map map);


    /**
     * 查询所有菜单状态
     * */
    List<SystemMenu> getSystemMenuList(Map map);
}
