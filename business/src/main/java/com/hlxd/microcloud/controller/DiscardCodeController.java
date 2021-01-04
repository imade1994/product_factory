package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.DiscardService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.CancelRelation;
import com.hlxd.microcloud.vo.DiscardCode;
import com.hlxd.microcloud.vo.DiscardCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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


    /**
     * 获取废码详情
     * */
    @RequestMapping("/getDiscardList")
    public Map getDiscardList(ServletRequest request){
        Map returnMap = new HashMap();
        List<DiscardCode> discardCodeList = discardService.getDiscardCodeList(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,discardCodeList);
        return returnMap;
    }
    /**
     * 获取解除关联码的列表
     * */
    @RequestMapping("/getCancelList")
    public Map getCancelList(ServletRequest request){
        Map returnMap = new HashMap();
        List<CancelRelation> cancelRelations = discardService.getCancelRelationList(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,cancelRelations);
        return returnMap;
    }

    /**
     * 获取废码回传记录
     * */
    @RequestMapping("/getDisCardCountList")
    public Map getUploadRecord(HttpServletRequest request){
        Map returnMap = new HashMap();
        Map paramMap  = CommonUtil.transformMap(request.getParameterMap());
        List<DiscardCount> discardCounts = discardService.getDisCardCountList(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,discardCounts);
        return returnMap;
    }

    /***
     * 手动上传
     * */
    @RequestMapping("/manualUpload")
    public Map manualUpload(@RequestParam("currentDate")String currentDate){
        Map map = new HashMap();
        Map returnMap = new HashMap();
        map.put("currentDate",currentDate);
        try{
            //返回废码合集
            // discardCodes = discardService.getDiscardCodeList(map);
            map.put("uploadState",1);
            map.put("uploadModel",2);//手动模式
            //更新上传记录
            discardService.updateDiscardCodeUpload(map);
            /**
             * 后续编码上传处理
             * */
        }catch (Exception e){
            ///出现异常回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"执行异常"+e.getMessage());
            return returnMap;
        }
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,"手动上传成功！");
        return returnMap;
    }











}
