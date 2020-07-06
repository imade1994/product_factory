package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemRoleMenuService;
import com.hlxd.microcloud.service.SystemRoleService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.SystemRole;
import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.hlxd.microcloud.util.CommonUtil.transformMap;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2610:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RequestMapping("/api/role")
@RestController
public class RoleController {

    @Autowired
    SystemRoleService systemRoleService;

    @Autowired
    SystemRoleMenuService systemRoleMenuService;

    @RequestMapping("/addRole")
    public Map addRole(SystemRole systemRole){
        Map returnMap = new HashMap();
        if(null != systemRole){
            systemRole.setId(UUID.randomUUID().toString());
            systemRole.setStatus(1);
            systemRoleService.addSystemRole(systemRole);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"新增对象不存在！");
        }
        return returnMap;
    }

    @RequestMapping("/deleteRole")
    @Transactional
    public Map deleteRole(@RequestParam("roleId")String roleId){
        Map returnMap = new HashMap();
        systemRoleService.deleteSystemRole(roleId);
        systemRoleMenuService.deleteSingleSystemRoleMenu(roleId);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/updateRole")
    @Transactional
    public Map updateRole(HttpServletRequest request){
        Map returnMap = new HashMap();
        Map paramMap = transformMap(request.getParameterMap());
        if(null != paramMap){
            if(null != paramMap.get("roleId")){
                List<String>  insertList = (List<String>) paramMap.get("insert");
                List<String>  deleteList = (List<String>) paramMap.get("delete");
                List<SystemRoleMenu> systemRoleMenus = new ArrayList<>();
                for(String s:insertList){
                    String[] ids = s.split(",");
                    if(ids.length<3){
                        returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                        returnMap.put(CommomStatic.MESSAGE,"参数长度有误！");
                        return returnMap;
                    }else{
                        SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                        systemRoleMenu.setId(UUID.randomUUID().toString());
                        systemRoleMenu.setMenuId(ids[0]);
                        systemRoleMenu.setRoleId(ids[1]);
                        systemRoleMenu.setMethodId(ids[2]);
                        systemRoleMenu.setStatus(1);
                        systemRoleMenus.add(systemRoleMenu);
                    }

                }
                systemRoleService.updateSystemRole(paramMap);
                if(insertList.size()>0){
                    systemRoleMenuService.addSystemRoleMenu(systemRoleMenus);
                }
                if(deleteList.size()>0){
                    systemRoleMenuService.deleteSystemRoleMenu(deleteList);
                }
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
            }else{
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,"必要参数为空！");
            }

        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"参数全部为空！");
        }
        return returnMap;
    }

    @RequestMapping("/getRoleDetail")
    public Map getRoleDetail(@RequestParam("roleId")String roleId){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("roleId",roleId);
        List<SystemRole> systemRoles = systemRoleService.getAuthorizationRole(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,systemRoles);
        return returnMap;
    }

    @RequestMapping("/getRole")
    public Map getRole(){
        Map returnMap = new HashMap();
        List<SystemRole> systemRoles = systemRoleService.getSystemRole();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,systemRoles);
        return returnMap;
    }







}
