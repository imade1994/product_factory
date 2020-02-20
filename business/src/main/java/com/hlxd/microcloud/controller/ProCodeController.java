package com.hlxd.microcloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.hlxd.microcloud.entity.FeedingRecord;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IFeedingService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import com.hlxd.microcloud.util.HttpClientUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.UTCToCST;
import static com.hlxd.microcloud.util.CommonUtil.influxDbDateToJsonObject;

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


    @Value("${timeBreak}")
    private String timeBreak;

    @Autowired
    private WrapService wrapService;

    @Autowired
    private ICodeService iCodeService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private IFeedingService iFeedingService;



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
    public Map getCode(@RequestParam("machineCode")String machineCode,@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate){




    }







}
