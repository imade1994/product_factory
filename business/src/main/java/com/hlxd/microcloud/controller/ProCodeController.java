package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.vo.ProCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Map getCodeDetails(@RequestParam("code")String code) throws ParseException {
        Map parameterMap = new HashMap();
        Map returnMap = new HashMap();
        ProCode proCode = influxDbDateToJsonObject(code);
        if(null != proCode){
            returnMap.put("status","1");
            returnMap.put("data",proCode);
        }else{
            returnMap.put("status","0");
            returnMap.put("msg","码不存在！");
        }
        return returnMap;
    }


    /**
     *产品追踪
     * */
    @RequestMapping("/codeCondition")
    public Map getCode(@RequestParam("machineCode")String machineCode,@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate,String qrCode){
        Map returnMap = new HashMap();
        if(null !=qrCode ){
            returnMap = findCode(machineCode, beginDate, endDate, qrCode);
        }else{
            returnMap =  getCodeCount(machineCode,beginDate,endDate);
        }
        return returnMap;
    }

    /**
     * 产品统计
     *
     * */
    @RequestMapping("/getCodeCount")
    public Map getCodeCountFromDate(@RequestParam(value = "beginDate",required = false)String beginDate,@RequestParam(value = "endDate",required = false)String endDate) throws ParseException {
        Map returnMap = new HashMap();
        if(null == beginDate || null == endDate){
            returnMap=getCountCodeCommen();
        }else{
            returnMap=getCodeCount(beginDate, endDate);
        }

        return returnMap;

    }







}
