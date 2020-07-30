package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.AnalysisService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.CountScan;
import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.ScanCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.reflect.internal.pickling.UnPickler;

import java.text.ParseException;
import java.util.*;

import static com.hlxd.microcloud.util.CommonUtil.compareTime;
import static com.hlxd.microcloud.util.CommonUtil.getFormateString;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1114:17
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/analysis")
@Slf4j
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;



    /**
     * 获取机台实时统计数据接口
     * @param machineCode 机台编码查询具体机台
     * */
    @RequestMapping("/getRealTime")
    public Map getMachineProduce(@RequestParam(value = "machineCode",required = false)String machineCode,@RequestParam("typeCode")String type){
        Map map = new HashMap();
        map.put("lastTime",getFormateString());
        //map.put("lastTime","2020-06-30");
        map.put("typeCode",type);
        if(null != machineCode){
            map.put("machineCode",machineCode);
        }
        List<ScanCount>  scanCounts = analysisService.getScanCount(map);
        map.clear();
        map.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        map.put(CommomStatic.DATA,scanCounts);
        return map;
    }

    /**
     * 获取所有机台的启动状态
     * */
     @RequestMapping("/getAllMachineStatus")
     public Map getAllMachineStatus(@RequestParam("typeCode")String type){
         Map param = new HashMap();
         param.put(CommomStatic.TYPE,type);
         List<Machine> machines = analysisService.getMachineStatus(param);
         param.clear();
         param.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
         param.put(CommomStatic.DATA,machines);
         return param;
     }

    /**
     * 获取统计产量
     * 暂时不区分同类机台，牌号
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param type 包装机还是装箱机
     * */
    @RequestMapping("/getPeriod")
    public Map getMachineProduceFromTo(@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate,@RequestParam("typeCode")String type,
                                       @RequestParam("queryType")int queryType,@RequestParam("groupBy")int groupBy){
        Map map = new HashMap();
        Map returnMap = new HashMap();
        map.put(CommomStatic.BEGINDATE,beginDate);
        map.put(CommomStatic.ENDDATE,endDate);
        map.put("groupBy",groupBy);
        map.put("type",type);
        List<CountScan> scanCountList = new ArrayList<>();
        switch (queryType){
            case 1:
                switch (groupBy){
                    case 1:
                        scanCountList = analysisService.getScanRateByMachineCode(map);
                        break;
                    case 2:
                        scanCountList = analysisService.getScanRateByBrand(map);
                        break;
                    case 3:
                        scanCountList = analysisService.getScanRateByDay(map);
                    default:
                        break;
                }
                break;
            case 2:
                switch (groupBy){
                    case 1:
                        scanCountList = analysisService.getRelateRateByMachineCode(map);
                        break;
                    case 2:
                        scanCountList = analysisService.getRelateRateByBrandId(map);
                        break;
                    case 3:
                        scanCountList = analysisService.getRelateRateByDay(map);
                    default:
                        break;
                }
                break;
            case 3:
                switch (groupBy){
                    case 1:
                        scanCountList = analysisService.getWorkRateByMachineCode(map);
                        break;
                    case 2:
                        scanCountList = analysisService.getWorkRateByBrandId(map);
                        break;
                    case 3:
                        scanCountList = analysisService.getWorkRateByDay(map);
                    default:
                        break;
                }
                break;
            case 4:
                scanCountList = analysisService.getRejectCount(map);
                break;
            case 5:
                switch (groupBy){
                    case 1:
                        scanCountList = analysisService.getCodeUseByDay(map);
                        break;
                    case 2:
                        scanCountList = analysisService.getCodeUseByMachine(map);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,scanCountList);
        return returnMap;
    }


    /**
     * 获取统计数据，
     * 1：产量
     * 2：剔除量
     * 3：读码率
     * 4：停机
     * 5：效率
     * @Param beginDate 开始时间
     * @Param endDate 结束时间
     * @Param type 机台类型
     * */
    @RequestMapping("/getCountFromPeriod")
    public Map getCountFromPeriod(@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate,@RequestParam("typeCode")String type){
        return new HashMap();
    }


    /**
     * 获取某日各机台详细产量
     * @param currentDate 查询日期
     * @param type 包装机还是装箱机
     * */
    @RequestMapping("/getProduceDetail")
    public Map getProduceDetail(@RequestParam("currentDate")String currentDate,@RequestParam("typeCode")String type){
        Map map = new HashMap();
        map.put(CommomStatic.TIME,currentDate);
        map.put(CommomStatic.TYPE,type);
        List<ScanCount> scanCountList = analysisService.getScanCount(map);
        map.clear();
        Map<String,List<ScanCount>> returnMap = new HashMap();
        for(ScanCount scanCount:scanCountList){
            List<ScanCount> scanCounts = returnMap.get(scanCount.getMachineName())==null?new ArrayList<>():returnMap.get(scanCount.getMachineName());
            scanCounts.add(scanCount);
            returnMap.put(scanCount.getMachineName(),scanCounts);
        }
        map.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        map.put(CommomStatic.DATA,returnMap);
        return map;
    }









}
