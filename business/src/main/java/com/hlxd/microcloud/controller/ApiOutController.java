package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.service.SoftManagementService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.FileUpload;
import com.hlxd.microcloud.vo.Brand;
import com.hlxd.microcloud.vo.SoftManagement;
import com.hlxd.microcloud.vo.SoftManagementRecordDetails;
import org.apache.kafka.clients.producer.internals.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/1510:56
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api")
public class ApiOutController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private SoftManagementService softManagementService;

    @RequestMapping("/brand/getBrandByCode")
    public Map getBrandByCode(@RequestParam("code")String Code){

        Map returnMap = new HashMap();
        Brand brand = brandService.getBrandByCode(Code);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,brand);
        return returnMap;

    }

    @RequestMapping("/soft/upload")
    @Transactional(rollbackFor = Exception.class)
    public Map uploadMachineSoft(MultipartFile file, SoftManagement softManagement,@RequestParam("machineCodes") List<String> machineCodes){
        Map returnMap   =  new HashMap();
        String fileName = FileUpload.Upload(file,CommomStatic.FILEPATH,softManagement.getSoftName()+"-"+softManagement.getLastVersion());
        //SoftManagement softManagement = new SoftManagement();
        softManagement.setFilePath(CommomStatic.FILEPATH);
        softManagement.setFileName(fileName);
        try{
            softManagementService.insertSoftManagementRecord(softManagement);
            int softId  = softManagement.getId();
            if(machineCodes.size()>0){
                softManagementService.batchAddSoftManagementRecordDetails(machineCodes,softId);
            }
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,"上传成功！");
        }catch (Exception e){
            /**
             * 上述过程发生异常，手动回滚事务
             * */
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,e.getMessage());
        }

        return returnMap;

    }
    /**
     * 只是更新记录，不涉及软件更新
     * */
    @RequestMapping("/soft/updateSoftManagementRecord")
    public Map updateSoft(SoftManagement softManagement){
        Map returnMap   =  new HashMap();
        if(null != softManagement){
            try{
                softManagementService.updateSoftManagementRecord(softManagement);
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,"UPDATE SUCCESS!");
            }catch (Exception e){
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,e.getMessage());
            }

        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"参数为空！");
        }
        return returnMap;
    }

    /**
     *删除软件上传以及关联记录
     * */
    @RequestMapping("/soft/deleteSoftManagementRecord")
    @Transactional(rollbackFor = Exception.class)
    public Map deleteSoftManagementRecord(@RequestParam("id")int id){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("id",String.valueOf(id));
        List<SoftManagement> softManagements = softManagementService.getSoftManagement(paramMap);
        try{
            softManagementService.deleteSoftManagementRecord(id);
            if(null != softManagements && softManagements.size()>0){
                List<SoftManagementRecordDetails> matchMachineCodes = softManagements.get(0).getMatchMachineCodes();
                List<Integer> ids = new ArrayList<>();
                matchMachineCodes.stream().forEach(matchMachineCode ->{
                    ids.add(matchMachineCode.getId());
                });
                softManagementService.batchDeleteSoftManagementRecordDetails(ids);
            }
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,"删除成功！");
        }catch (Exception e){
            /**
             * 上述过程发生异常，手动回滚事务
             * */
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"删除失败！错误信息为"+e.getMessage());
        }
        return returnMap;
    }

    /**
     * 校验最新版本
     * */
    @RequestMapping("/soft/validateLastVersion")
    public Map validateLastVersion(@RequestParam("machineCode")String machineCode,@RequestParam("softName")String softName){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("validate","1");
        paramMap.put("machineCode",machineCode);
        paramMap.put("softName",softName);
        List<SoftManagement> softManagements = softManagementService.getSoftManagement(paramMap);
        if(softManagements.size()>0){
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,softManagements.get(0).getLastVersion());
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"暂无版本信息！");
        }
        return returnMap;
    }





}
