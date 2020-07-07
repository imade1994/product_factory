package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/715:16
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;



    @RequestMapping("/getBrandList")
    public Map getBrandList(ServletRequest servletRequest){
        Map param = CommonUtil.transformMap(servletRequest.getParameterMap());
        Map returnMap = new HashMap();
        List<Brand> brands = brandService.getBrandList(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,brands);
        return returnMap;
    }

    @RequestMapping("/insertBrand")
    public Map insertBrand(List<Brand> brands){
        Map returnMap = new HashMap();
        if(null != brands && brands.size()>0){
            brandService.insertBrand(brands);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"新增对象不存在！");
        }
        return returnMap;
    }

    @RequestMapping("/updateBrand")
    public Map updateBrand(Brand brand){
        Map returnMap = new HashMap();
        if(null != brand){
            brandService.updateBrand(brand);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }
        return returnMap;
    }










}
