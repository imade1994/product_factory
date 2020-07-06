package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemMenuMapper;
import com.hlxd.microcloud.service.SystemMenuService;
import com.hlxd.microcloud.vo.MenuMeta;
import com.hlxd.microcloud.vo.SystemMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2515:20
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {
    @Autowired
    SystemMenuMapper systemMenuMapper;



    @Override
    public void updateSystemMenu(Map menuMeta) {
        systemMenuMapper.updateSystemMenu(menuMeta);
    }

    @Override
    public void changeStatus(Map systemMenu) {
        systemMenuMapper.changeStatus(systemMenu);
    }

    @Override
    public List<SystemMenu> getSystemMenuList(Map map) {
        return systemMenuMapper.getSystemMenuList(map);
    }
}
