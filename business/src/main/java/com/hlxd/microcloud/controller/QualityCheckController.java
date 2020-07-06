package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.QualityCheckService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.RandomCheckRecord;
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
 * @Date 2020/7/313:43
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/quality")
public class QualityCheckController {

    @Autowired
    private QualityCheckService qualityCheckService;


    @RequestMapping("/getCheckRecord")
    public Map getCheckRecord(ServletRequest request){
        Map param = CommonUtil.transformMap(request.getParameterMap());
        Map returnMap = new HashMap();
        List<RandomCheckRecord> randomCheckRecords = qualityCheckService.getRandomCheckList(param);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,randomCheckRecords);
        return returnMap;
    }




}
