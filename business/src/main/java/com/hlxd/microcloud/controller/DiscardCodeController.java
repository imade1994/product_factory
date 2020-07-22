package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.DiscardService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.CancelRelation;
import com.hlxd.microcloud.vo.DiscardCode;
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
 * @Date 2020/7/1515:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/discard")
public class DiscardCodeController {


    @Autowired
    DiscardService discardService;



    @RequestMapping("/getDiscardList")
    public Map getDiscardList(ServletRequest request){
        Map returnMap = new HashMap();
        List<DiscardCode> discardCodeList = discardService.getDiscardCodeList(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,discardCodeList);
        return returnMap;
    }

    @RequestMapping("/getCancelList")
    public Map getCancelList(ServletRequest request){
        Map returnMap = new HashMap();
        List<CancelRelation> cancelRelations = discardService.getCancelRelationList(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,cancelRelations);
        return returnMap;
    }










}
