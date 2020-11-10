package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/1510:56
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/brand")
public class ApiOutController {
    @Autowired
    private BrandService brandService;

    @RequestMapping("/getBrandByCode")
    public Map getBrandByCode(@RequestParam("code")String Code){

        Map returnMap = new HashMap();
        Brand brand = brandService.getBrandByCode(Code);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,brand);
        return returnMap;

    }



}
