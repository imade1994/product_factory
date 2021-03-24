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
    @Transactional(rollbackFor = Exception.class)
    public Map addSoft(SoftVo soft,@RequestParam("machineModel") List<String> machineModel){
        Map returnMap = new HashMap();
        int softId = softManagementService.insertSoft(soft);
        if(machineModel.size()>0){
            softManagementService.batchAddSoftManagementRecordDetails(machineModel,softId);
        }
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return  returnMap;
    }

    @RequestMapping("/soft/updateSoft")
    @Transactional(rollbackFor = Exception.class)
    public Map updateSoft(HttpServletRequest request,@RequestParam("addMachineModels")List<String> addMachineModel,@RequestParam("deleteMachineModels")List<Integer> deleteMachineModel){
        Map returnMap = new HashMap();
        Map<String,String> paramMap  = CommonUtil.transformMap(request.getParameterMap());
        try {
            softManagementService.updateSoft(paramMap);
            List<SoftVo> softs = softManagementService.getSoftManagement(paramMap);
            if(null !=softs && softs.size()>0){
                SoftVo softVo = softs.get(0);
                List<SoftManagementRecordDetails> softManagementRecordDetails = softVo.getMatchMachineModel();
                if(addMachineModel.size()>0){
                    for(String s:addMachineModel){
                        for(SoftManagementRecordDetails softManagementRecordDetails1:softManagementRecordDetails){
                            if(s.equals(softManagementRecordDetails1.getMachineModel())){
                                //移除，重复添加编码
                                addMachineModel.remove(s);
                            }
                        }
                    }
                }
                if(addMachineModel.size()>0){
                    softManagementService.batchAddSoftManagementRecordDetails(addMachineModel,softVo.getId());
                }
                if(deleteMachineModel.size()>0){
                    softManagementService.batchDeleteSoftManagementRecordDetails(deleteMachineModel);
                }
            }

            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }catch (Exception e){
            /**
             * 上述过程发生异常，手动回滚事务
             * */
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
                    List<SoftManagementRecordDetails> matchMachineCodes = softList.get(0).getMatchMachineModel();
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
    public Map uploadMachineSoft(MultipartFile file, SoftManagement softManagement){
        Map returnMap   =  new HashMap();
        String fileName = FileUpload.Upload(file,CommomStatic.FILEPATH,softManagement.getSoftName()+"-"+softManagement.getLastVersion());
        //SoftManagement softManagement = new SoftManagement();
        softManagement.setFilePath(CommomStatic.FILEPATH);
        softManagement.setFileName(fileName);
        try{
            softManagementService.insertSoftManagementRecord(softManagement);
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
    public Map updateSoft(SoftManagement softManagement,MultipartFile file){
        Map returnMap   =  new HashMap();
        if(null != softManagement){
            try{
                //获取之前的记录
                Map paramMap = new HashMap();
                paramMap.put("id",softManagement.getId());
                SoftManagement oldSoft = softManagementService.getSoftVersion(paramMap);
                if(null != file){
                    //删除旧文件
                    FileUpload.deleteFile(CommomStatic.FILEPATH+oldSoft.getFileName());
                    //上传新文件
                    String fileName = FileUpload.Upload(file,CommomStatic.FILEPATH,softManagement.getSoftName()+"-"+softManagement.getLastVersion());
                    softManagement.setFileName(fileName);
                }
                //更新版本记录
                softManagementService.updateSoftManagementRecord(softManagement);
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
    public Map deleteSoftManagementRecord(@RequestParam("id")int id,@RequestParam("softId")int softId){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("id",String.valueOf(id));
        paramMap.put("softId",String.valueOf(softId));
        SoftManagement softManagement = softManagementService.getSoftVersion(paramMap);
        if(null == softManagement){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"删除失败！版本不存在！");
        }else{
            try{
                softManagementService.deleteSoftManagementRecord(id);
                FileUpload.deleteFile(CommomStatic.FILEPATH+softManagement.getFileName());
                /*List<Integer> ids = new ArrayList<>();
                List<SoftManagementRecordDetails> matchMachineCodes = softManagement.getMatchMachineCodes();
                matchMachineCodes.stream().forEach(matchMachineCode ->{
                    ids.add(matchMachineCode.getId());
                });
                if(ids.size()>0){
                    softManagementService.batchDeleteSoftManagementRecordDetails(ids);
                }*/
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
    public Map validateLastVersion(@RequestParam("softId")String softId,@RequestParam("version")String version){
        Map paramMap  = new HashMap();
        Map returnMap = new HashMap();
        paramMap.put("validate","1");
        paramMap.put("softId",softId);
        paramMap.put("version",version);
        List<SoftVo> softs = softManagementService.getSoftManagement(paramMap);
        if(softs.size()>0){
            List<Map> mapList = new ArrayList<>();
            for(SoftVo soft:softs){
                Map tem = new HashMap();
                tem.put("versionId",soft.getSoftVersions().get(0).getId());
                tem.put("version",soft.getSoftVersions().get(0).getLastVersion());
                tem.put("remark",soft.getSoftVersions().get(0).getSoftRemark());
                mapList.add(tem);
            }

            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,mapList);
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
    public void downLoadSoft(@RequestParam("softId")int softId,@RequestParam("versionId")int versionId, HttpServletResponse response,HttpServletRequest request){
        Map paramMap = new HashMap();
        paramMap.put("softId",softId);
        paramMap.put("id",versionId);
        SoftManagement softManagement = softManagementService.getSoftVersion(paramMap);
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
