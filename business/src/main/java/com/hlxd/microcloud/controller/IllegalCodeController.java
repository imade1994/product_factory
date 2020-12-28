package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.IllegalCodeService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.IllegalCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2817:32
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@RestController
@RequestMapping("/api/illegalCode")
public class IllegalCodeController {

    @Autowired
    private IllegalCodeService illegalCodeService;




    @RequestMapping("/getIllegalCodeCount")
    public Map getIllegalCodeCount(HttpServletRequest request){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap = CommonUtil.transformMap(request.getParameterMap());
        List<IllegalCode> illegalCodes = illegalCodeService.getIllegalCodeCount(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,illegalCodes);
        return returnMap;
    }

    @RequestMapping("/getIllegalCode")
    public Map getIllegalCode(HttpServletRequest request){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap = CommonUtil.transformMap(request.getParameterMap());
        List<IllegalCode> illegalCodes = illegalCodeService.getIllegalCode(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,illegalCodes);
        return returnMap;
    }



}
