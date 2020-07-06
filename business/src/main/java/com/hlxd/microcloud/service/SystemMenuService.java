package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.MenuMeta;
import com.hlxd.microcloud.vo.SystemMenu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface SystemMenuService {
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
