package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.CodeBatchService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.CodeBatch;
import com.hlxd.microcloud.vo.CodeBatchDetails;
import com.hlxd.microcloud.vo.CodeDetail;
import com.hlxd.microcloud.vo.UploadRecord;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1910:02
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/batch")
public class CodeBatchController {

    @Autowired
    CodeBatchService codeBatchService;


    @RequestMapping("/getBatch")
    public Map getBatch(ServletRequest request){
        Map returnMap = new HashMap();
        Map param = CommonUtil.transformMap(request.getParameterMap());
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,codeBatchService.getCodeBatch(param));
        return returnMap;
    }

    @RequestMapping("/getCodeDetail")
    public Map getCodeDetail(@RequestParam("batchNo")String batchNo){
        Map returnMap = new HashMap();
        Map param = new HashMap();
        param.put("batchNo",batchNo);
        param.put("packageType",1);
        List<CodeDetail> codeDetails = codeBatchService.getCodeDetail(param);
        Map temMap = new HashMap();
        param.replace("packageType",2);
        List<CodeDetail> codeDetails1 = codeBatchService.getCodeDetail(param);
        temMap.put("1",codeDetails);
        temMap.put("2",codeDetails1);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,temMap);
        return returnMap;
    }

    @RequestMapping(value = "createNewBatch")
    public Map createNewBatch(CodeBatch codeBatch){
        Map returnMap = new HashMap();
        String tableName = CommomStatic.TABLE_NAME_PRIFIX+RandomStringUtils.randomAlphanumeric(10);
        codeBatchService.createNewTable(tableName);
        //codeBatch.setTableName(tableName);
        codeBatchService.insertBatch(codeBatch);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/getUploadRecord")
    public Map getUploadRecord(String produceDate){
        Map returnMap = new HashMap();
        List<UploadRecord> uploadRecordList = codeBatchService.getUploadRecord(produceDate);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,uploadRecordList);
        return returnMap;
    }

    @RequestMapping("/manualUpload")
    public Map manualUpload(@RequestParam("id")int id){
        Map returnMap = new HashMap();
        int count = codeBatchService.validateUploadRecord(id);
        if(count>0){//先查询是否是同步失败任务
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.DATA,CommomStatic.wrongTags.get(CommomStatic.illegal_operation));
        }else{
            if(true){
                ///手动触发任务 任务执行成功返回true
                codeBatchService.updateUploadRecord(id);
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.DATA,CommomStatic.SUCCESS_MESSAGE);
            }else{
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.wrongTags.get(CommomStatic.lack_Params));
            }
        }
        return returnMap;
    }


    /**
     * 手动抽检接口
     *
     * */
    @RequestMapping("/validateBatchCode")
    public Map validateBatchCode(@RequestParam("batchNo")String batchNo,@RequestParam("qrCode")String qrCode,@RequestParam("packageType") BigInteger packageType){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("batchNo",batchNo);
        paramMap.put("qrCode",qrCode);
        paramMap.put("packageType",packageType);
        int count  = codeBatchService.validateBatchCode(paramMap);
        CodeBatchDetails codeBatchDetails = new CodeBatchDetails();
        codeBatchDetails.setBatchNo(batchNo);
        codeBatchDetails.setPackageType(packageType);
        codeBatchDetails.setQrCode(qrCode);
        switch (count)
        {
            case 0:
                codeBatchDetails.setCheckStatus(0);
                returnMap.put(CommomStatic.DATA,0);
                break;
            case 1:
                codeBatchDetails.setCheckStatus(1);
                returnMap.put(CommomStatic.DATA,1);
                break;
            default:
                codeBatchDetails.setCheckStatus(2);
                returnMap.put(CommomStatic.DATA,2);
                break;
        }
        codeBatchService.insertCheckDetails(codeBatchDetails);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        return returnMap;
    }



    /**
     * 拒收，或者放行批次
     *
     * */
        @RequestMapping("/updateBatchStatus")
    public Map updateBatchStatus(@RequestParam("batchNo")String batchNo,@RequestParam("checkStatus")Integer checkStatus){
        Map paramMap = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("batchNo",batchNo);
        paramMap.put("checkStatus",checkStatus);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,CommomStatic.SUCCESS_MESSAGE);
        codeBatchService.updateBatchStatus(paramMap);
        return returnMap;
    }



}
