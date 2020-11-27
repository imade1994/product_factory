package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemUserService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemUser;
import com.hlxd.microcloud.vo.SystemUserRole;
import com.hlxd.microcloud.vo.UserSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 用户注册接口
     * */
    @RequestMapping("/register")
    @Transactional
    public Map userRegister(@RequestBody UserSync userSync){

        Map returnMap = new HashMap();
        if(null != userSync && null != userSync.getAccount()){
            int count  = userService.countUserName(userSync.getAccount());
            if(count<=0){
                SystemUser systemUser = new SystemUser();
                String userId = UUID.randomUUID().toString();
                systemUser.setUserName(userSync.getAccount());
                systemUser.setPassWord("hlxd@82777999");
                systemUser.setId(userId);
                systemUser.setDepartment("ADMIN");
                systemUser.setCreatePeople("门户注册");
                systemUser.setStatus(1);
                userService.addUser(systemUser);
                SystemUserRole systemUserRole = new SystemUserRole();
                systemUserRole.setDepartment_id("10ac82ad-dd2b-11ea-97ee-286ed488cd06");
                systemUserRole.setUserId(userId);
                systemUserRole.setStatus(1);
                userService.addDepartmentRole(systemUserRole);
                returnMap.put("code",1);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
            }else{
                returnMap.put("code",0);
                returnMap.put(CommomStatic.MESSAGE,"用户名已存在！");
            }
        }else{
            returnMap.put("code",0);
            returnMap.put(CommomStatic.MESSAGE,"参数异常！");
        }

        return returnMap;
    }



    /**
     *用户验证接口
     * */
    @RequestMapping("/validateAccount/{account}")
    public Map validateUser(@PathVariable("account") String account){
        Map returnMap  = new HashMap();
        int count = userService.countUserName(account);
        if(count>0){
            returnMap.put("code","1");
            returnMap.put("data",true);
        }else{
            returnMap.put("code","1");
            returnMap.put("data",false);
        }
        return returnMap;

    }


    /**
     * 用户注销接口
     * */
    @RequestMapping("/logOut/{account}")
    @Transactional
    public Map LogoutFormDoor(@PathVariable("account")String account){
        Map param = new HashMap();
        Map returnMap = new HashMap();
        param.put("userNameComplete",account);
        List<SystemUser>  systemUsers = userService.getAllUser(param);
        if(systemUsers.size()>0){
            String userId = systemUsers.get(0).getId();
            userService.deleteUserByAccount(userId);
            userService.deleteUserDepartment(userId);
            returnMap.put("code",1);
            returnMap.put("msg","success");
        }else{
            returnMap.put("code",1);
            returnMap.put("msg","用户不存在！");
        }
        return returnMap;
    }



}
