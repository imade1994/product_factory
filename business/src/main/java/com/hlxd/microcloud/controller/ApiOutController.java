package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.service.SoftManagementService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.FileUpload;
import com.hlxd.microcloud.vo.Brand;
import com.hlxd.microcloud.vo.SoftVo;
import com.hlxd.microcloud.vo.SoftManagement;
import com.hlxd.microcloud.vo.SoftManagementRecordDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
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
@Slf4j
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

    @RequestMapping("/soft/addSoft")
    public Map addSoft(SoftVo soft){
        Map returnMap = new HashMap();
        softManagementService.insertSoft(soft);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return  returnMap;
    }

    @RequestMapping("/soft/updateSoft")
    public Map updateSoft(HttpServletRequest request){
        Map returnMap = new HashMap();
        Map<String,String> paramMap  = CommonUtil.transformMap(request.getParameterMap());
        try {
            softManagementService.updateSoft(paramMap);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"更新失败！"+e.getMessage());
        }
        return returnMap;
    }

    @RequestMapping("/soft/deleteSoft")
    @Transactional(rollbackFor = Exception.class)
    public Map deleteSoft(@RequestParam("id")int id){
        Map returnMap = new HashMap();
        Map paramMap  = new HashMap();
        try {
            paramMap.put("id",id);
            List<SoftVo> softList = softManagementService.getSoftManagement(paramMap);
            //删除软件
            softManagementService.deleteSoft(id);
            List<SoftManagement> softManagements = softList.get(0).getSoftVersions();
            for(SoftManagement softManagement:softManagements){
                //删除软件下属版本信息
                softManagementService.deleteSoftManagementRecord(softManagement.getId());
                //删除软件关联设备信息
                if(null != softManagements && softManagements.size()>0){
                    FileUpload.deleteFile(CommomStatic.FILEPATH+softManagement.getFileName());
                    List<SoftManagementRecordDetails> matchMachineCodes = softManagement.getMatchMachineCodes();
                    List<Integer> ids = new ArrayList<>();
                    matchMachineCodes.stream().forEach(matchMachineCode ->{
                        ids.add(matchMachineCode.getId());
                    });
                    softManagementService.batchDeleteSoftManagementRecordDetails(ids);
                }
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
            int versionId  = softManagement.getId();
            if(machineCodes.size()>0){
                softManagementService.batchAddSoftManagementRecordDetails(machineCodes,versionId);
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
    public Map updateSoft(SoftManagement softManagement,@RequestParam("addMachineCodes")List<String> addMachineCodes,@RequestParam("deleteMachineCodes")List<Integer> deleteMachineCodes,MultipartFile file){
        Map returnMap   =  new HashMap();
        if(null != softManagement){
            try{
                //获取之前的记录
                SoftManagement oldSoft = softManagementService.getSoftVersion(softManagement.getId());
                //删除旧文件
                FileUpload.deleteFile(CommomStatic.FILEPATH+oldSoft.getFileName());
                //上传新文件
                String fileName = FileUpload.Upload(file,CommomStatic.FILEPATH,softManagement.getSoftName()+"-"+softManagement.getLastVersion());
                softManagement.setFileName(fileName);
                //更新版本记录
                softManagementService.updateSoftManagementRecord(softManagement);
                if(addMachineCodes.size()>0){
                    softManagementService.batchAddSoftManagementRecordDetails(addMachineCodes,softManagement.getId());
                }
                if(deleteMachineCodes.size()>0){
                    softManagementService.batchDeleteSoftManagementRecordDetails(deleteMachineCodes);
                }
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,"更新成功!");
            }catch (Exception e){
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,"更新失败！"+e.getMessage());
            }

        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"参数为空！");
        }
        return returnMap;
    }

    /**
     *删除软件版本信息以及上传以及关联记录
     * */
    @RequestMapping("/soft/deleteSoftManagementRecord")
    @Transactional(rollbackFor = Exception.class)
    public Map deleteSoftManagementRecord(@RequestParam("id")int id){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("id",String.valueOf(id));
        SoftManagement softManagement = softManagementService.getSoftVersion(id);
        if(null == softManagement){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"删除失败！版本不存在！");
        }else{
            try{
                softManagementService.deleteSoftManagementRecord(id);
                FileUpload.deleteFile(CommomStatic.FILEPATH+softManagement.getFileName());
                List<Integer> ids = new ArrayList<>();
                List<SoftManagementRecordDetails> matchMachineCodes = softManagement.getMatchMachineCodes();
                matchMachineCodes.stream().forEach(matchMachineCode ->{
                    ids.add(matchMachineCode.getId());
                });
                if(ids.size()>0){
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
        paramMap.put("validateSoftName",softName);
        List<SoftVo> softs = softManagementService.getSoftManagement(paramMap);
        if(softs.size()>0){
            SoftVo soft = softs.get(0);
            Map tem = new HashMap();
            tem.put("versionId",soft.getSoftVersions().get(0).getId());
            tem.put("lastVersion",soft.getSoftVersions().get(0).getLastVersion());
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,tem);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"暂无版本信息！");
        }
        return returnMap;
    }

    /**
     * 获取软件信息
     * */
    @RequestMapping("/soft/getSoftManagementRecords")
    public Map getSoftManagementRecords(HttpServletRequest request){
        Map paramMap = CommonUtil.transformMap(request.getParameterMap());
        List<SoftVo> softList =softManagementService.getSoftManagement(paramMap);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,softList);
        return returnMap;
    }

    /**
     * 下载软件
     * */
    @RequestMapping("/soft/downLoadSoft")
    public void downLoadSoft(@RequestParam("versionId")int versionId, HttpServletResponse response,HttpServletRequest request){
        SoftManagement softManagement = softManagementService.getSoftVersion(versionId);
        if(null != softManagement){
            try{
                String fileName = softManagement.getFileName();
                String filePath = CommomStatic.FILEPATH;
                InputStream is  = new FileInputStream(filePath+fileName);
                response.reset();
                response.setContentType("bin");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + FileUpload.getFilename(request.getHeader("user-agent"),softManagement.getFileName()) + "\"");
                byte[]b = new byte[1024];
                int len;
                while((len = is.read(b))>0 ){
                    response.getOutputStream().write(b,0,len);
                    is.close();
                }
            }catch (Exception e){
                log.error("*********************系统下载功能异常"+e.getMessage());
            }
        }
    }
}
