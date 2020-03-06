package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.vo.ProCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2914:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@RestController
@RequestMapping("/pro")
public class ProCodeController {

    /**
     *编码查询
     *
     * */
    @RequestMapping("/code")
    public Map getCodeDetails(@RequestParam("code")String code) throws ParseException, IllegalAccessException, InstantiationException {
        Map returnMap = influxDbDateToJsonObject(code);
        return returnMap;
        //return null;
    }


    /**
     *产品统计
     * */
    @RequestMapping("/codeCondition")
    public Map getCode(HttpServletRequest request) throws IllegalAccessException, InstantiationException, ParseException {
        Map paramMap = request.getParameterMap();
        Map returnMap = getCountFromPeriod(paramMap);
        return returnMap;
    }

    /**
     * 产品追踪
     *
     * */
    @RequestMapping("/getCodeCount")
    public Map getCodeCountFromDate(@RequestParam(value = "beginDate",required = false)String beginDate,@RequestParam(value = "endDate",required = false)String endDate) throws ParseException {
        Map returnMap = new HashMap();
        if(null == beginDate || null == endDate){
           // returnMap=getCountCodeCommen();
        }else{
            //returnMap=getCodeCount(beginDate, endDate);
        }

        return returnMap;

    }







}
