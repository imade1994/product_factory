package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemUserService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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



    /**
     * 新增用户
     *
     * */
    @RequestMapping("/addUser")
    public Map addUser(SystemUser systemUser){
        Map returnMap = new HashMap();
        if(null != systemUser){
            systemUser.setId(UUID.randomUUID().toString());
            userService.addUser(systemUser);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }
        return returnMap;
    }

    /**
     * 删除用户
     * */
    @RequestMapping("/deleteUser")
    public Map deleteUser(@RequestParam("id")String id){
        Map returnMap = new HashMap();
        userService.deleteUser(id);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }


    /**
     * 更新用户信息
     * */
    @RequestMapping("/updateUser")
    public Map updateUser(ServletRequest request){
        Map param = CommonUtil.transformMap(request.getParameterMap());
        Map returnMap = new HashMap();
        if(!param.containsKey("id")){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }else{
            userService.updateUser(param);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }
        return returnMap;
    }


    @RequestMapping("/getAllUser")
    public Map getAllUser(ServletRequest request){
        Map returnMap = new HashMap();
        Map param = CommonUtil.transformMap(request.getParameterMap());
        List<SystemUser> systemUsers = userService.getAllUser(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,systemUsers);
        return returnMap;
    }

    /**
     *新增用户菜单
     *
    @RequestMapping("/addUserMenu")
    public Map addUserMenu(@RequestParam("userId")String userId,@RequestParam("menuId")String menuId){
        Map param = new HashMap();
        param.put("id",UUID.randomUUID().toString());
        param.put("userId",userId);
        param.put("menuId",menuId);
        param.put("methodId","")



    }*/

    /**
     * 查询用户授权菜单
     * */
    @RequestMapping("/getUserMenu")
    public Map getUserMenu(@RequestParam("id")String id){
        Map param = new HashMap();
        Map returnMap = new HashMap();
        param.put("userId",id);
        List<SystemMenu> menuList=userService.getUserMenu(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,menuList);
        return returnMap;
    }



}
