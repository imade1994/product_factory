package com.hlxd.microcloud.rest;


import com.alibaba.fastjson.JSON;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.SysUser;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.feign.AuthService;
import com.hlxd.microcloud.service.UserService;
import com.hlxd.microcloud.util.JedisPoolUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String key = "444235786425912D";

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public Map  getUser(
            @RequestParam(value = "userName") String username,
            @RequestParam(value = "passWord") String password) throws Exception {
        Map returnMap = new HashMap();
        SysUser user = userService.getUser(username, password);
        if(null != user){
            Map paramMap = new HashMap();
            paramMap.put("username", username);
            paramMap.put("password", password);
            paramMap.put("grant_type", "password");
            paramMap.put("scope", "all");
            returnMap =authService.getToken(paramMap);
            UserInfo userInfo = userService.getUserInfo(username,password);
            JedisPoolUtils.setPool(0,String.valueOf(returnMap.get("access_token")),JSON.toJSONString(userInfo));
        }else{
            returnMap.put("msg","用户不存在");
        }
        return returnMap;
    }

    @GetMapping("/userInfo")
    public R<UserInfo> getUserInfo(
            @RequestParam(value = "userName") String username,
            @RequestParam(value = "passWord") String password) throws Exception {
        return new R<>(userService.getUserInfo(username, password));
    }






}

