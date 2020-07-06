package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.CodeBatchService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.CodeBatch;
import com.hlxd.microcloud.vo.UploadRecord;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
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
    public Map getCodeDetail(@RequestParam("tableName")String tableName){
        Map returnMap = new HashMap();
        Map param = new HashMap();
        //判断参数中是否存在非法字符串
        if(CommonUtil.validateParams(tableName)==0){
            param.put("tableName",tableName);
            param.put("type","1");
            Map map = new HashMap();
            map.put("1",codeBatchService.getCodeDetail(param));
            param.replace("type","2");
            map.put("2",codeBatchService.getCodeDetail(param));
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.DATA,map);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.wrongTags.get(CommomStatic.illegal_Params));
        }
        return returnMap;
    }

    @RequestMapping(value = "createNewBatch")
    public Map createNewBatch(CodeBatch codeBatch){
        Map returnMap = new HashMap();
        String tableName = CommomStatic.TABLE_NAME_PRIFIX+RandomStringUtils.randomAlphanumeric(10);
        codeBatchService.createNewTable(tableName);
        codeBatch.setTableName(tableName);
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



}
