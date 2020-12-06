package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.service.ProCodeService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.ThreadManager;
import com.hlxd.microcloud.vo.CodeUnion;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.ProductionCount;
import com.hlxd.microcloud.vo.TableSplit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.SQLSyntaxErrorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

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
    public Map getProductionByPeriod(@RequestParam("machineCode")String machineCode,@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate) throws ParseException, ExecutionException, InterruptedException {
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
        List<ProductionCount> productionCounts = new ArrayList<>();
        List<String> produceDates = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ScheduleDate);
        Date beginDate_new = simpleDateFormat.parse(beginDate);
        Date endDate_new   = simpleDateFormat.parse(endDate);
        boolean flag = false;
        paramMap.clear();
        paramMap.put("beginDate",beginDate);
        paramMap.put("endDate",endDate);
        paramMap.put("machineCode",machineCode);
        paramMap.put("tableName","");
        paramMap.put("type","");
        int packageCount = 0;
        int stripCount   = 0;
        int itemCount    = 0;
        List<FutureTask<Boolean>> futureTasks  = new ArrayList<>();
        for(TableSplit tableSplit:tableSplits){
            try {
                /**
                 * 返回结果集已按照时间正序排序了
                 * 有四种满足结果关系
                 * beginDate_new  date_beginDate  date_endDate endDate_new 2
                 * date_beginDate  beginDate_new  endDate_new date_endDate 1 需跳出循环
                 * date_beginDate beginDate_new    date_endDate endDate_new 3
                 * beginDate_new  date_beginDate endDate_new date_endDate  4 需跳出循环
                 * */
                int  results = compareDate(beginDate_new,endDate_new,tableSplit.getBeginDate(),tableSplit.getEndDate());
                if(results!=0){
                    Callable<Boolean> queryCall = new Callable<Boolean>() {
                        @Override
                        public Boolean call()  {
                            Map tmpMap = new HashMap();
                            try{
                                tmpMap.put("beginDate",beginDate);
                                tmpMap.put("endDate",endDate);
                                tmpMap.put("machineCode",machineCode);
                                tmpMap.put("tableName",UnionTableNamePrefix+tableSplit.getProduceDate());
                                tmpMap.put("type",results);
                                ProductionCount productionCount = initService.getCountFromUnion(tmpMap);
                                productionCounts.add(productionCount);
                                produceDates.add(tableSplit.getProduceDate());
                            }catch (Exception e){
                                log.error(LOG_ERROR_PREFIX+"****************多线程查询"+tmpMap.get("tableName")+"***************异常信息为"+e.getMessage());
                                return true;
                            }
                            return true;
                        }
                    };
                    FutureTask<Boolean> CodeUnion = new FutureTask<>(queryCall);
                    futureTasks.add(CodeUnion);
                    ThreadManager.getLongPool().execute(CodeUnion);
                }
                switch (results){
                    case 1:
                    case 4:
                        flag = true;
                        break;
                    default:
                        break;
                }
                if(flag){
                    break;
                }
            }catch (Exception e){
                log.error(LOG_ERROR_PREFIX+"*************日期转换异常！数据库查询日期"+tableSplit.getEndDate()+"**********************************查询时间"+endDate);
            }
        }
        /**
         * 此时已经拿到相关日期 拼接 UnionTableNamePrefix 即可获得 相关表名
         * 考虑到可能条包装可能晚一天
         * 拿到的日期最后一天再往后推一天
         * */
        for(FutureTask<Boolean> futureTask:futureTasks){
            if(futureTask.get()){
            }
        }
        paramMap.replace("tableName",UnionTableNamePrefix+(Integer.valueOf(produceDates.get(produceDates.size()-1))+1));
        paramMap.replace("type",4);
        try {
            ProductionCount productionCount = initService.getCountFromUnion(paramMap);
            productionCounts.add(productionCount);
        }catch (BadSqlGrammarException e){
            log.info("***************************向后一天推迟一天表不存在**************************");
        }
        for(ProductionCount production:productionCounts){
            packageCount = packageCount+production.getPackageCount();
            stripCount   = stripCount+production.getStripCount();
            itemCount    = itemCount+production.getItemCount();
        }
        ProductionCount newProduction = new ProductionCount();
        newProduction.setPackageCount(packageCount);
        newProduction.setStripCount(stripCount);
        newProduction.setItemCount(itemCount);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,newProduction);
        return returnMap;
    }


    /**
     * 产品追踪
     *
     * */
    @RequestMapping("/validateCode")
    public Map validateCode(@RequestParam("beginDate")String beginDate,@RequestParam("endDate")String endDate,@RequestParam("machineCode")String machineCode,@RequestParam("qrCode")String qrCode){
        /*String result = (String) redisTemplate.opsForValue().get(qrCode);
        Map<String,String> param = CommonUtil.splitResults(result);
        if(null != param && !param.isEmpty()){
            String relationDate_redis = param.get("relationDate");
            String packageType_redis = param.get("packageType");
            String machineCode_redis = param.get("machineCode");
            switch (packageType_redis){
                case "1":
                    *//**
                     * 获取到码详细信息
                     * *//*
                    String tableName = initService.getTableScheduleString(machineCode_redis,relationDate_redis);
                    ProCode proCode = initService.getProCode(tableName,qrCode);
                    if(null != proCode){

                    }
                    break;
                case "2":
                    *//**
                     * 包装规格 2 跟 3 查询方式一致
                     * *//*
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


        }*/


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
