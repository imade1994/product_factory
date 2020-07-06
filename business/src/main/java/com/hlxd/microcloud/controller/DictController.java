package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.SystemDictService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.SystemDict;
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
 * @Date 2020/5/2610:23
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RequestMapping("/api/dict")
@RestController
public class DictController {

    @Autowired
    SystemDictService systemDictService;



    @RequestMapping("/getDict")
    public Map getDictByParam(){
        Map param = new HashMap();
        List<SystemDict> systemDicts = systemDictService.getAllDictList(param);
        param.clear();
        param.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        param.put(CommomStatic.DATA,systemDicts);
        return param;
    }


    @RequestMapping("/addDict")
    public Map addDict(SystemDict systemDict){
        Map returnMap = new HashMap();
        returnMap=systemDictService.insertSystemDict(systemDict);
        return returnMap;
    }

    @RequestMapping("/deleteDict")
    public Map deleteDict(@RequestParam("id")String id){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("id",id);
        returnMap=systemDictService.deleteSystemDict(paramMap);
        return returnMap;
    }
    @RequestMapping("/updateDict")
    public Map updateDict(SystemDict systemDict){
        Map returnMap = new HashMap();
        systemDictService.updateSystemDict(systemDict);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }












}
