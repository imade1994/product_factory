package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.UploadRecordService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.UploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2811:13
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@RestController
@RequestMapping("/api/uploadRecord")
public class UploadRecordController {
    @Autowired
    UploadRecordService uploadRecordService;

    /**
     * 获取
     * 数据同步记录
     * 以下未非必要参数
     * @param beginDate
     * @param endDate
     * @param uploadModel
     * */
    @RequestMapping("/getUploadRecord")
    public Map getUploadRecord(HttpServletRequest request){
        Map paramMap                        = CommonUtil.transformMap(request.getParameterMap());
        Map returnMap                       = new HashMap();
        List<UploadRecord> uploadRecordList = uploadRecordService.getUploadRecord(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,uploadRecordList);
        return returnMap;
    }

    /**
     * 手动上传编码
     *
     * 必要参数
     * @param id
     * **/
    @RequestMapping("/manualUpload")
    public Map manualUpload(@RequestParam("id")String id){
        Map paramMap                     = new HashMap();
        Map returnMap                    = new HashMap();
        paramMap.put("id",id);
        List<UploadRecord> uploadRecords = uploadRecordService.getUploadRecord(paramMap);
        if(null != uploadRecords && uploadRecords.size()>0){
            /**
             * 业务逻辑处理
             * */
            //-------
            paramMap.clear();
            paramMap.put("uploadModel",2);//数据上传模式
            paramMap.put("status",1);//数据上传完成状态
            paramMap.put("id",uploadRecords.get(0).getId());
            uploadRecordService.updateUploadRecord(paramMap);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,"Upload Success!");
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,"数据任务不存在!");
        }
        return returnMap;
    }







}
