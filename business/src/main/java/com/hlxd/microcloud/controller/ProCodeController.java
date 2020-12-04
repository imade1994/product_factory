package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.service.ProCodeService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.ProductionCount;
import com.hlxd.microcloud.vo.TableSplit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/api/pro")
@Slf4j
public class ProCodeController {

    @Autowired
    private ProCodeService proCodeService;

    @Autowired
    private InitService initService;

    /**
     *编码查询
     *
     * */
    @RequestMapping("/code")
    public Map getCodeDetails(@RequestParam("code")String code) throws ParseException, IllegalAccessException, InstantiationException {
        Map paraMap = new HashMap();
        Map returnMap = new HashMap();
        paraMap.put("qrCode",code);
        ProCode proCode = proCodeService.getProCode(paraMap);
        if(null == proCode){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"码不存在！");
        }else{
            switch (proCode.getPackageType()){
                case 1:
                    paraMap.replace("qrCode",proCode.getParentCode());
                    ProCode parentCode = proCodeService.getSingleProCode(paraMap);
                    paraMap.replace("qrCode",parentCode.getParentCode());
                    ProCode stripCode  = proCodeService.getSingleProCode(paraMap);
                    List<ProCode> packList = new ArrayList<>();
                    packList.add(proCode);
                    parentCode.setProCodes(packList);
                    List<ProCode> stripList = new ArrayList<>();
                    stripList.add(parentCode);
                    stripCode.setProCodes(stripList);
                    stripCode.setOriginPack(proCode.getPackageType());
                    returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                    returnMap.put(CommomStatic.DATA,stripCode);
                    break;
                case 2:
                    paraMap.replace("qrCode",proCode.getParentCode());
                    ProCode stripCode1  = proCodeService.getSingleProCode(paraMap);
                    if(null != stripCode1){
                        List<ProCode> proCodes = new ArrayList<>();
                        proCodes.add(proCode);
                        stripCode1.setProCodes(proCodes);
                        stripCode1.setOriginPack(proCode.getPackageType());
                        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                        returnMap.put(CommomStatic.DATA,stripCode1);
                    }else{
                        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                        returnMap.put(CommomStatic.DATA,proCode);
                    }
                    break;
                case 3:
                    List<ProCode> proCodes = proCode.getProCodes();
                    List<ProCode> proCodeList = new ArrayList<>();
                    for(ProCode proCode1:proCodes){
                        paraMap.replace("qrCode",proCode1.getQrCode());
                        ProCode proCode2 = proCodeService.getProCode(paraMap);
                        proCodeList.add(proCode2);
                    }
                    proCode.getProCodes().clear();
                    proCode.setProCodes(proCodeList);
                    proCode.setOriginPack(proCode.getPackageType());
                    returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                    returnMap.put(CommomStatic.DATA,proCode);
                    break;
                default:
                    returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                    returnMap.put(CommomStatic.MESSAGE,"规格异常！");
                    break;
            }
        }
        return returnMap;
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
    @RequestMapping("/getCode")
    public Map getCodeCountFromDate(HttpServletRequest request) throws ParseException, IllegalAccessException, InstantiationException {
        Map paramMap = request.getParameterMap();
        Map returnMap = getCodeCountFromPeriod(paramMap);
        return returnMap;
    }


    /**
     * 产量统计
     *
     * */
    @RequestMapping("/getProduction")
    public Map getProductionByPeriod(@RequestParam("machineCode")String machineCode,@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate){
        Map paramMap = new HashMap();
        List<String> dateStrs  =  new ArrayList<>();
        try {
            dateStrs   = getDayFromDate(beginDate,endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paramMap.put("machineCode",machineCode);
        paramMap.put("dates",dateStrs);
        List<TableSplit> tableSplits = initService.getTableSplit(paramMap);
        List<String> tableNames = new ArrayList<>();
        for(TableSplit tableSplit:tableSplits){
            try {
                boolean flag = compareDate(tableSplit.getEndDate(),endDate);
                if(flag){
                    tableNames.add(tableSplit.getTableName());
                    break;
                }else{
                    tableNames.add(tableSplit.getTableName());
                }
            }catch (Exception e){
                log.error(LOG_ERROR_PREFIX+"*************日期转换异常！数据库查询日期"+tableSplit.getEndDate()+"**********************************查询时间"+endDate);
            }
        }








        Map returnMap = new HashMap();
        paramMap.put("machineCode",machineCode);
        paramMap.put("beginDate",beginDate);
        paramMap.put("endDate",endDate);
        ProductionCount productionCount = proCodeService.getProductionByPeriod(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,productionCount);
        return returnMap;
    }


    /**
     * 产品追踪
     *
     * */
    @RequestMapping("/validateCode")
    public Map validateCode(@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate,@RequestParam("machineCode")String machineCode,@RequestParam("qrCode")String qrCode){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("machineCode",machineCode);
        paramMap.put("beginDate",beginDate);
        paramMap.put("endDate",endDate);
        paramMap.put("qrCode",qrCode);
        int count = proCodeService.validateCode(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,count);
        return returnMap;
    }


    /**
     * 范围性码查找
     * @param machineCode,beginDate,endDate,qrCode
     *
     * */
    @RequestMapping("/findCode")
    public Map findCodeFromPeriod(HttpServletRequest request){
        Map paramMap = request.getParameterMap();
        Map returnMap = findCode(paramMap);
        return returnMap;
    }


   /* @RequestMapping("/validate")
    public int validateCode(@RequestParam(value = "code",required = true)String code){
        Jedis jedis = JedisPoolUtils.getJedis();
        if(null != jedis){
            if(jedis.exists(code)){
                jedis.del(code);
                jedis.close();
                return 1;
            }else{
                jedis.close();
                return 0;
            }
        }else{
            return 3;
        }
    }*/







}
