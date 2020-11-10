package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.RejectService;
import com.hlxd.microcloud.service.TimeBreakService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.RejectDetails;
import com.hlxd.microcloud.vo.TimeBreak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.transformMap;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2514:37
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/timeBreak")
public class TimeBreakController {



    @Autowired
    private TimeBreakService timeBreakService;



    @RequestMapping("/getTimeBreakDetails")
    public Map getTimeBreakDetails(HttpServletRequest request){
        Map returnMap = new HashMap();
        Map paramMap = transformMap(request.getParameterMap());
        if (!paramMap.containsKey("pageNum")
                || !paramMap.containsKey("queryType")
                || !paramMap.containsKey("pageSize")
                || !paramMap.containsKey("typeCode")) {
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.wrongTags.get(CommomStatic.lack_Params));
        }else{
            int pageSize = Integer.valueOf(String.valueOf(paramMap.get("pageSize")));
            int fromIndex  = (Integer.valueOf(String.valueOf(paramMap.get("pageNum")))-1)*Integer.valueOf(String.valueOf(paramMap.get("pageSize")));
            paramMap.put("fromIndex",fromIndex);
            paramMap.replace("pageSize",pageSize);
            List<TimeBreak> timeBreak = timeBreakService.getTimeBreak(paramMap);
            int count = timeBreakService.TimeBreakCount(paramMap);
            paramMap.clear();
            paramMap.put("total",count);
            paramMap.put("data",timeBreak);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.DATA,paramMap);
        }
        return returnMap;
    }






}
