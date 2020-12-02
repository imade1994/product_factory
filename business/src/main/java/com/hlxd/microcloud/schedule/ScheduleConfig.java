package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.AnalysisService;
import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.ThreadManager;
import com.hlxd.microcloud.vo.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import static com.hlxd.microcloud.util.CommonUtil.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/2813:23
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Component
@RestController
@Slf4j
public class ScheduleConfig {

    static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .readTimeout(100,TimeUnit.SECONDS)
            .writeTimeout(100,TimeUnit.SECONDS);

    /** influx 连接工厂 */
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://192.168.2.8:8086",client);

    public static final String dataBase = "hlxd";

    public static final String timestamp = "timestamp";

    public static ScheduleConfig scheduleConfig;



    @Autowired
    AnalysisService analysisService;

    @Autowired
    BatchTaskService batchTaskService;

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @Autowired
    InitService initService;

    @Autowired
    AsyncService asyncService;



    @PostConstruct
    public void init(){
        scheduleConfig = this;
        scheduleConfig.analysisService = this.analysisService;
        scheduleConfig.batchTaskService = this.batchTaskService;
        //unionCode();
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public  void unionCode(){
        /**
         * 查询当前是否有线程在操作数据库
         * */
        String lock = (String) redisTemplate.opsForValue().get("t_hl_system_code_error_lock");
        log.info(LOG_INFO_PREFIX+"******************************定时任务主动查询关联触发当前锁值为"+lock);
        List<FutureTask<Boolean>> futureTasks = new ArrayList<>();
        if(null == lock){
            /**
             * 保证同时只有一个线程操作数据合并
             * */
            redisTemplate.opsForValue().set("t_hl_system_code_error_lock","1");
            List<ScheduleErrorCode> scheduleErrorCodes = batchTaskService.getErrorCode(0);
            for(ScheduleErrorCode scheduleErrorCode:scheduleErrorCodes){
                Callable<Boolean> queryCall = new Callable<Boolean>() {
                    @Override
                    public Boolean call()  {
                        try{
                            Map map = new HashMap();
                            map.put("itemCode",scheduleErrorCode.getQrCode());
                            /**
                             * 获取组装好的3码关联关系实体
                             * */
                            List<CodeUnion> codeUnions = batchTaskService.getCodeUnionByItemCode(map);
                            if(null != codeUnions&&codeUnions.size()>0){
                                batchTaskService.BatchInsertCodeUnion(scheduleErrorCode.getTableName(),codeUnions);
                                /**
                                 * 更新异常主库ID
                                 * 状态码 1:为执行完成可以执行删除
                                 * */
                                batchTaskService.updateSchedule(scheduleErrorCode.getId(),1);
                                /**
                                 * 从主库移除数据
                                 *
                                batchTaskService.deleteCodeFromSystemCode(scheduleErrorCode.getQrCode());
                                *//**
                                 * 从执行异常库删除数据
                                 * *//*
                                batchTaskService.deleteSchedule(scheduleErrorCode.getId());*/
                            }
                        }catch (Exception e){
                            log.error(LOG_ERROR_PREFIX+"****************定时任务出现异常二维码为"+scheduleErrorCode.getQrCode()+"***************异常信息为"+e.getMessage());
                            return true;
                        }
                        return true;
                    }
                };
                FutureTask<Boolean> CodeUnion = new FutureTask<>(queryCall);
                futureTasks.add(CodeUnion);
                ThreadManager.getLongPool().execute(CodeUnion);
            }
            Boolean flag = true;
            /**
             * 轮询多线程任务结果
             * 执行完成会返回 true;
             * */
            for(FutureTask<Boolean> futureTask:futureTasks){
                try {
                    if(!futureTask.get()){
                        flag = false;
                    }
                } catch (InterruptedException e) {
                    log.error(LOG_ERROR_PREFIX+"****************获取多线程结果出现异常***************异常信息为"+e.getMessage());
                } catch (ExecutionException e) {
                    log.error(LOG_ERROR_PREFIX+"****************获取多线程结果出现异常***************异常信息为"+e.getMessage());
                    e.printStackTrace();
                }
            }
            if(flag){
                /**
                 * 从redis中移除锁
                 * */
                redisTemplate.delete("t_hl_system_code_error_lock");
            }
        }



    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateMachineTime(){
        List<InitMachineTimeVo> machineTimeList1 =  initService.getInitMachineTime();
        List<InitMachineTimeVo> machineTimeList2 =  initService.getInitMachineTimeFromTable();
        for(InitMachineTimeVo initMachineTimeVo:machineTimeList1){
            for(InitMachineTimeVo initMachineTimeVo1:machineTimeList2){
                if(initMachineTimeVo.getMachineCode().equals(initMachineTimeVo1.getMachineCode())){
                    boolean flag = compactDetails(initMachineTimeVo,initMachineTimeVo1);
                    if(!flag){
                        log.info(LOG_INFO_PREFIX+"*********************换班，更换班组为+"+initMachineTimeVo.getClassName()+"更换前为+"+initMachineTimeVo1.getClassName());
                        initService.updateMachineTime(initMachineTimeVo);
                    }
                }
            }



        }
        //log.info("**********************当前未发生换班**********************************");
    }
    public boolean compactDetails(InitMachineTimeVo vo1,InitMachineTimeVo vo2){
        if(vo1.getBeginDate().equals(vo2.getBeginDate())&&vo1.getEndDate().equals(vo2.getEndDate())){
            return true;
        }else{
            return false;
        }
    }
   @Scheduled(cron = "0 0/1 * * * ? ")
    public void initTable(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ScheduleDate);
        List<InitTableSchedule> tableSchedules = initService.getInitTableScheduleFromTable();
        if(null != tableSchedules&& tableSchedules.size()>0){
            for(InitTableSchedule initTableSchedule:tableSchedules){
                try{
                    Date beginDate = simpleDateFormat.parse(initTableSchedule.getBeginDate());
                    Date endDate   = simpleDateFormat.parse(initTableSchedule.getEndDate());
                    Date nowDate   = new Date();
                    if(nowDate.before(beginDate)||  nowDate.after(endDate)){
                        log.info(LOG_INFO_PREFIX+"******************************当前时间不在时间段内，触发更新");
                        InitTableSchedule tableSchedule = initService.getInitTableSchedule(initTableSchedule.getMachineCode());
                        initService.updateTableSchedule(tableSchedule);
                        String tableName = TableNamePrefix+tableSchedule.getDateString()+tableSchedule.getMachineCode();
                        initService.createNewTable(tableName);
                        /**
                         * 往表格生成表里面插入一条记录
                         * */
                        InitTable initTable = new InitTable();
                        initTable.setTableName(tableName);
                        initTable.setProduceDate(tableSchedule.getDateString());
                        initTable.setProduceMachineCode(tableSchedule.getMachineCode());
                        initTable.setScheduleBeginDate(tableSchedule.getBeginDate());
                        initTable.setScheduleEndDate(tableSchedule.getEndDate());
                        initService.insertRecordTableInit(initTable);
                    }else{
                        //log.info("无需处理***************************************");
                    }
                }catch (Exception e){
                    log.info(LOG_ERROR_PREFIX+"*********************************日期转换异常！"+e.getMessage());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0 0/1 * * * ? ")
    public void deleteCodeFromBase(){
        log.info(LOG_INFO_PREFIX+"******************************删除任务触发");
        List<ScheduleErrorCode> scheduleErrorCodes = batchTaskService.getErrorCode(1);
        for(ScheduleErrorCode scheduleErrorCode:scheduleErrorCodes){
            /**
             * 从主库移除数据
             **/
             batchTaskService.deleteCodeFromSystemCode(scheduleErrorCode.getQrCode());
             /**
             * 从执行异常库删除数据
             * */
             batchTaskService.deleteSchedule(scheduleErrorCode.getId());
        }

    }


   /* @Scheduled(cron = "0 0 0 * * ?")
    @RequestMapping("/upload")
    //@PostConstruct
    public static void upload() throws ParseException, InstantiationException, IllegalAccessException {
        //influxDb.enableBatch()
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000L;
        //Query query = new Query("select * from code where time >= 1587698070000000000 and time <= 1588043674000000000 and type !='3'  group by type,parentCode",dataBase);
        Query query = new Query("select * from code where time>="+beginDate+"000000"+" and time <="+endDate+"000000"+" and type !='3'  group by type,parentCode",dataBase);
        //Query query = new Query("select * from code where  type !='3'  group by type,parentCode",dataBase);
        log.info("查询sql为*********************"+query.getCommand()+"************************************************");
        if(null !=influxDb.ping()){
            log.info("数据库连接正常********************************************************************");
            QueryResult queryResult = influxDb.query(query);
            Map<String,Map<String,List<ProCode>>> map = new HashMap<>();
            if(null != queryResult && null != queryResult.getResults()){
                for(QueryResult.Result result:queryResult.getResults()){
                    List<QueryResult.Series> series = result.getSeries();
                    log.info("数组大小-*----------------------"+series.size());
                    if(null != series){
                        for(QueryResult.Series serie:series){
                            List proCodes = new ArrayList<>();
                            proCodes.addAll(CommonUtil.getQueryData(serie,ProCode.class));
                            Map temMap = new HashMap();
                            temMap.put(serie.getTags().get("parentCode"),proCodes);
                            if(map.containsKey(serie.getTags().get("type"))){
                                map.get(serie.getTags().get("type")).putAll(temMap);
                            }else{
                                map.put(serie.getTags().get("type"),temMap);
                            }
                            //map.put(serie.getTags().get("type"),proCodes);
                        }
                        insertCodeRelationFromMap(map);
                    }
                }
            }
        }else{
            log.info("数据库连接异常********************************************************************");
        }



    }
*/

    //@Scheduled(cron = "0 0 0 * * ?")
    @RequestMapping("/count")
    //@PostConstruct
    public static void count() throws ParseException, InstantiationException, IllegalAccessException {
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000L;
        //Query query = new Query("select count(*) from code  where time >1587571200001000000 and time <=1587657600001000000  group by time(8h),type,machineCode",dataBase);
        Query query = new Query("select count(*) from code  where time >"+beginDate+"000000"+" and time <="+endDate+"000000"+"  group by time(8h),type,machineCode",dataBase);
        log.info("查询sql为*********************"+query.getCommand()+"************************************************");
        if(null != influxDb.ping()){
            log.info("数据库连接正常********************************************************************");
            QueryResult queryResult = influxDb.query(query);
            List<CodeCount> proCodes = new ArrayList<>();
            if(null != queryResult && null != queryResult.getResults()){
                for(QueryResult.Result result:queryResult.getResults()){
                    List<QueryResult.Series> series = result.getSeries();
                    if(null != series){
                        for(QueryResult.Series serie:series){
                            String type = serie.getTags().get("type");
                            String machineCode = serie.getTags().get("machineCode");
                            List<CodeCount> temList = getQueryData(serie, CodeCount.class);
                            for(CodeCount codeCount:temList){
                                Date temDate = UTCToStr1(codeCount.getTime());
                                codeCount.setType(type);
                                codeCount.setMachineCode(machineCode);
                                codeCount.setDate(temDate.getTime());
                                codeCount.setPeriod(getPeriod(codeCount.getTime()));
                            }
                            proCodes.addAll(temList);
                        }
                    }
                    if(proCodes.size()>0){
                        insertCodeCountByPeriod(proCodes);
                    }
                }
            }
        }else{
            log.info("数据库连接异常********************************************************************");
        }

    }

    public static boolean uploadStatistic(String date){
        return true;
    }

    //@PostConstruct
    public static boolean initUpload(){
        String str  = CommonUtil.getFormateString();
        uploadStatistic(str);
        return true;
    }

    //@Scheduled(cron = "0 0 0 * * ?")
    public void initStatisticByDay(){
        




    }




}
