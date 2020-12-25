package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.service.QualityCheckService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.ThreadManager;
import com.hlxd.microcloud.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.hlxd.microcloud.util.CommonUtil.UnionTableNamePrefix;
import static com.hlxd.microcloud.util.CommonUtil.tableScheduleTime;

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

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private InitService initService;


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
        switch (packageType){
            case "2":
                param.put("packageType",1);
                break;
            case "3":
                param.put("packageType",2);
                break;
            default:
                break;
        }
        Map returnMap  = new HashMap();
        ProCode proCode = qualityCheckService.getCodeDetail(param);
        returnMap.put(CommomStatic.DATA,proCode);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        return returnMap;
    }


    @RequestMapping("/getCodeRelation")
    public Map getCodeRelation(@RequestParam("qrCode")String qrCode) throws  ParseException {
        String results = (String) redisTemplate.opsForValue().get(qrCode);
        Map returnMap = new HashMap();
        ProCode proCodes = null;
        /**
         * result 返回格式应为 machineCode=4584584825,relationDate=2020-11-26 12:12:12,packageType=1
         * 对result进行分割 获取对应的值
         * */
        Map<String,String> param = CommonUtil.splitResults(results);
        if(null != param && param.size()>0&& !(param.isEmpty()) ){
            String packageType = param.get("packageType");
            String machineCode = param.get("machineCode");
            String relationDate = param.get("relationDate");
            switch (packageType){
                case "1":
                    /**
                     * 通过查询当日数据库查询得包码的对应条码
                     * 再从redis中获取条码的关联时间
                     * */
                    String tableName = initService.getTableScheduleString(machineCode,relationDate);
                    ProCode proCode = initService.getProCode(tableName,qrCode);
                    if(null != proCode){
                        String results_1 = (String) redisTemplate.opsForValue().get(proCode.getParentCode());
                        if(null != results_1){
                            proCodes = getProCodes(results_1,qrCode);
                            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                            returnMap.put(CommomStatic.DATA,proCodes);
                        }else{
                            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                            List<ProCode> packageList = new ArrayList<>();
                            List<ProCode> itemList    = new ArrayList<>();
                            ProCode itemProCode = new ProCode();
                            ProCode packageProCode = new ProCode();
                            packageList.add(proCode);
                            packageProCode.setProCodes(packageList);
                            packageProCode.setQrCode(proCode.getParentCode());
                            packageProCode.setRelationDate(proCode.getRelationDate());
                            packageProCode.setMachineName(proCode.getMachineName());
                            itemList.add(packageProCode);
                            itemProCode.setMachineName("暂未关联");
                            itemProCode.setRelationDate("暂未关联");
                            itemProCode.setQrCode("未关联");
                            itemProCode.setProCodes(itemList);
                            returnMap.put(CommomStatic.DATA,itemProCode);
                        }
                    }
                    break;
                case "2":
                    /**
                     * 包装规格 2 跟 3 查询方式一致
                     * */
                    proCodes = getProCodes(results,qrCode);
                    returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                    returnMap.put(CommomStatic.DATA,proCodes);
                    break;
                case "3":
                    proCodes = getProCodes(results,qrCode);
                    returnMap.put(CommomStatic.DATA,proCodes);
                    returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                    break;
                default:
                    break;
            }
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"数据异常，请联系管理员！");
        }
/*      ProCode proCode = qualityCheckService.getCodeRelation(qrCode);
        Map param = new HashMap();
        Map returnMap = new HashMap();
        if(null == proCode){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"码暂未全部关联！");
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.DATA,proCode);
        }*/
        return returnMap;
    }

    /**
     * 抽出公用代码段
     *
     * */
     ProCode getProCodes(String results,String qrCode) throws ParseException {
        Map<String,String> tmp = CommonUtil.splitResults(results);
        String tableName_1 = UnionTableNamePrefix+tableScheduleTime(tmp.get("relationDate"));
        tmp.clear();
        tmp.put("tableName",tableName_1);
        tmp.put("qrCode",qrCode);
        ProCode proCodes = initService.getProCodes(tmp);
        return proCodes;
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
