package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemMenuService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.MenuMeta;
import com.hlxd.microcloud.vo.SystemMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2610:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RequestMapping("/api/menu")
@RestController
public class MenuController {
    @Autowired
    SystemMenuService systemMenuService;


    @RequestMapping("/updateMenu")
    public Map updateMenu(@RequestParam("menuId")String menuId, @RequestParam("title")String title){
        Map returnMap = new HashMap();
        Map param = new HashMap();
        param.put("id",menuId);
        param.put("title",title);
        systemMenuService.updateSystemMenu(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/changeMenuStatus")
    public Map changeMenuStatus(@RequestParam("menuId")String menuId,@RequestParam("status")String status){
        Map returnMap = new HashMap();
        Map param = new HashMap();
        param.put("menuId",menuId);
        param.put("status",status);
        systemMenuService.changeStatus(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }


    @RequestMapping("/getMenu")
    public Map getMenuByParam(){
        Map returnMap = new HashMap();
        List<SystemMenu> systemMenus = systemMenuService.getSystemMenuList(returnMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,systemMenus);
        return returnMap;
    }









}
