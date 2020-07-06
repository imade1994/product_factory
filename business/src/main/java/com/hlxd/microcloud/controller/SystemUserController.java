package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemUserService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.SystemUser;
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
 * @Date 2020/5/2610:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/user")
public class SystemUserController {

    @Autowired
    SystemUserService userService;




    @RequestMapping("/login")
    public Map userLogin(@RequestParam("username")String username,@RequestParam("password")String password){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("username",username);
        paramMap.put("password",password);
        List<SystemUser> systemUsers = userService.login(paramMap);
        if(null != systemUsers&& systemUsers.size()>0){
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.DATA,systemUsers.get(0));
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"用户不存在");
        }
        return returnMap;
    }

}
