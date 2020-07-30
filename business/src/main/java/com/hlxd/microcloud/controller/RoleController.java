package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemRoleMenuService;
import com.hlxd.microcloud.service.SystemRoleService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.SystemMenu;
import com.hlxd.microcloud.vo.SystemRole;
import com.hlxd.microcloud.vo.SystemRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Map deleteRole(@RequestParam("department_id")String department_id){
        Map returnMap = new HashMap();
        systemRoleService.deleteSystemRole(department_id);
        systemRoleMenuService.deleteSingleSystemRoleMenu(department_id);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }


    /**
     * 更新部门
     *
     * */
    @RequestMapping("/updateDepartment")
    public Map updateSystemRole(SystemRole systemRole){
        Map returnMap = new HashMap();
        if(null != systemRole && null !=systemRole.getId())
        systemRoleService.updateSystemRole(systemRole);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;

    }
    /**
     * 新增角色菜单
     * */
    @RequestMapping("/updateRoleMenu")
    @Transactional
    public Map updateRole(@RequestParam("id")String roleId,@RequestParam("menuId")String menuId,
                          @RequestParam("status")Integer status){
        Map returnMap = new HashMap();
        SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
        systemRoleMenu.setId(UUID.randomUUID().toString());
        systemRoleMenu.setDepartmentId(roleId);
        systemRoleMenu.setMenuId(menuId);
        systemRoleMenu.setStatus(status);
        systemRoleMenu.setMethodId("8969cb38-ba72-11ea-8ade-286ed488cd06");
        systemRoleMenuService.addSystemRoleMenu(systemRoleMenu);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }
    /**
     * 删除角色菜单
     * */
    @RequestMapping("/deleteRoleMenu")
    public Map deleteRoleMenu(@RequestParam("departmentId")String departmentId,@RequestParam("menuId")String menuId){
        Map param = new HashMap();
        Map returnMap = new HashMap();
        param.put("departmentId",departmentId);
        param.put("menuId",menuId);
        systemRoleMenuService.deleteSystemRoleMenu(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/getRoleDetail")
    public Map getRoleDetail(@RequestParam("department_id")String department_id){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("department_id",department_id);
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


    @RequestMapping("/getDepartmentMenu")
    public Map getDepartmentMenu(@RequestParam("id")String departmentId){
        Map param = new HashMap();
        Map returnMap = new HashMap();
        param.put("id",departmentId);
        List<SystemMenu> systemMenus  = systemRoleService.getDepartmentMenu(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,systemMenus);
        return returnMap;





    }










}
