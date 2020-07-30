package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.QualityCheckService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.RandomCheckDetails;
import com.hlxd.microcloud.vo.RandomCheckRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
@Slf4j
public class QualityCheckController {

    @Autowired
    private QualityCheckService qualityCheckService;


    @RequestMapping("/getCheckRecord")
    public Map getCheckRecord(ServletRequest request){
        Map returnMap = new HashMap();
        Map param = CommonUtil.transformMap(request.getParameterMap());
        List<RandomCheckRecord> randomCheckRecords = qualityCheckService.getRandomCheckList(param);
        returnMap.put(CommomStatic.DATA,randomCheckRecords);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        return returnMap;
    }

    @RequestMapping("/codeDetails")
    public Map getCodeDetails(@RequestParam("qrCode")String qrCode,@RequestParam("packageType")String packageType){
        Map param = new HashMap();
        param.put("qrCode",qrCode);
        param.put("packageType",packageType);
        Map returnMap  = new HashMap();
        ProCode proCode = qualityCheckService.getCodeDetail(param);
        returnMap.put(CommomStatic.DATA,proCode);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        return returnMap;
    }


    @RequestMapping("/check")
    public Map checkQrCodeRelation(@RequestBody RandomCheckRecord randomCheckRecord){
        Map param = new HashMap();
        Map returnMap = new HashMap();
        if(null != randomCheckRecord){
            String qrCode = randomCheckRecord.getQrCode();
            if(null != qrCode){
                param.put("qrCode",qrCode);
                ProCode proCode = qualityCheckService.getCodeDetail(param);
                randomCheckRecord.setBrandId(proCode.getBrandId());
                randomCheckRecord.setMachineCode(proCode.getMachineCode());
                randomCheckRecord.setShiftId(proCode.getShiftId());
                //List<RandomCheckRecord> randomCheckRecords = qualityCheckService.getRandomCheckList(param);
                //int newId = null==randomCheckRecords.get(0)?1:randomCheckRecords.get(0).getId()+1;
                String newId = UUID.randomUUID().toString();
                randomCheckRecord.setId(newId);
                qualityCheckService.insertRandomCheck(randomCheckRecord);
                List<RandomCheckDetails> randomCheckDetails = randomCheckRecord.getRandomCheckDetails();
                for(RandomCheckDetails randomCheckDetails1:randomCheckDetails){
                    randomCheckDetails1.setId(UUID.randomUUID().toString());
                    randomCheckDetails1.setRandomCheckId(newId);
                }
                if(null != randomCheckDetails || randomCheckDetails.size()>0){
                    qualityCheckService.insertRandomCheckDetails(randomCheckDetails);
                }
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
            }else{
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
            }
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }

        return  returnMap;
    }




}
