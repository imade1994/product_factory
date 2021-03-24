package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.*;
import com.hlxd.microcloud.util.CommomStatic;
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

import static com.hlxd.microcloud.util.CommomStatic.REDIS_UNION_CODE_LOCK;
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
//@Component
//@RestController("/api/manual")
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

    @Autowired
    DiscardService discardService;

    @Autowired
    UploadRecordService uploadRecordService;



    @PostConstruct
    public void init(){
        scheduleConfig = this;
        scheduleConfig.analysisService     = this.analysisService;
        scheduleConfig.batchTaskService    = this.batchTaskService;
        scheduleConfig.discardService      = this.discardService;
        scheduleConfig.uploadRecordService = this.uploadRecordService;
        //unionCode();
        //delRedis();
        //unionCode();
        try {
            uploadSuccessCode();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void delRedis(){
       Set<String> keys = redisTemplate.keys("T_HL_SYSTEM_CODE_LOCK*");
       for(String s:keys){
           log.info("************************删除keys"+s);
           redisTemplate.delete(s);
       }
    }

    @Scheduled(cron = "0 0/5 * * * ? ")
    public  void unionCode(){
        /**
         * 查询当前是否有线程在操作数据库
         * */
        //String lock = (String) redisTemplate.opsForValue().get("t_hl_system_code_error_lock");
        //log.info(LOG_INFO_PREFIX+"******************************定时任务主动查询关联触发当前锁值为"+lock);
        List<FutureTask<String>> futureTasks = new ArrayList<>();
        List<ScheduleErrorCode> scheduleErrorCodes = batchTaskService.getErrorCode(0);
        for(ScheduleErrorCode scheduleErrorCode:scheduleErrorCodes) {
            /**
             * 保证同时只有一个线程操作一条数据合并
             * */
            /*redisTemplate.delete(REDIS_UNION_CODE_LOCK+scheduleErrorCode.getQrCode());
            String lock = null;*/
            String lock = (String) redisTemplate.opsForValue().get(REDIS_UNION_CODE_LOCK+scheduleErrorCode.getQrCode());
            if(null == lock){
                Callable<String> queryCall = new Callable<String>() {
                    @Override
                    public String call()  {
                        try{
                            redisTemplate.opsForValue().set(REDIS_UNION_CODE_LOCK+scheduleErrorCode.getQrCode(),'1');
                            Map map = new HashMap();
                            map.put("itemCode",scheduleErrorCode.getQrCode());
                            /*
                 * 获取组装好的3码关联关系实体
                 */
                            List<CodeUnion> codeUnions = batchTaskService.getCodeUnionByItemCode(map);
                            if(null != codeUnions&&codeUnions.size()>0){
                                int count = initService.checkTableExits(scheduleErrorCode.getTableName());
                                if(!(count>0)){
                                    //log.info("*************表"+tableName+"不存在，手动创建！**********************************");
                                    initService.createNewUnionTable(scheduleErrorCode.getTableName());
                                }
                                /*
                 * 先执行删除再同步插入
                 */
                                batchTaskService.deleteItemBeforeInsert(scheduleErrorCode.getTableName(),scheduleErrorCode.getQrCode());

                                batchTaskService.BatchInsertCodeUnion(scheduleErrorCode.getTableName(),codeUnions);
                                /*
                 * 更新异常主库ID
                 * 状态码 1:为执行完成可以执行删除
                 */
                                //batchTaskService.updateSchedule(scheduleErrorCode.getId(),1);
                                /*
                 * 从主库移除数据
                 */
                 batchTaskService.deleteCodeFromSystemCode(scheduleErrorCode.getQrCode());
                /*
                 * 从执行异常库删除数据
                 */
                                batchTaskService.deleteSchedule(scheduleErrorCode.getId());
                            }
                        }catch (Exception e){
                            log.error(LOG_ERROR_PREFIX+"****************定时任务出现异常二维码为"+scheduleErrorCode.getQrCode()+"***************异常信息为"+e.getMessage());
                            return "";
                        }finally {
                            redisTemplate.delete(REDIS_UNION_CODE_LOCK+scheduleErrorCode.getQrCode());
                        }
                        return scheduleErrorCode.getQrCode();
                    }
                };
                FutureTask<String> CodeUnion = new FutureTask<>(queryCall);
                futureTasks.add(CodeUnion);
                ThreadManager.getLongPool().execute(CodeUnion);
            }
        }
        /*
                 * 轮询多线程任务结果
                 * 执行完成会返回 true;
                 */
        for(FutureTask<String> futureTask:futureTasks){
            try {
                String code = futureTask.get();
                /*
                 * 删除redis锁
                 * 释放资源
                 */
                redisTemplate.delete(REDIS_UNION_CODE_LOCK+code);
            } catch (InterruptedException e) {
                log.error(LOG_ERROR_PREFIX+"****************获取多线程结果出现异常***************异常信息为"+e.getMessage());
            } catch (ExecutionException e) {
                log.error(LOG_ERROR_PREFIX+"****************获取多线程结果出现异常***************异常信息为"+e.getMessage());
                e.printStackTrace();
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

    @Scheduled(cron = "0 30 1 * * ?")
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

    @Scheduled(cron = "0 0 2 * * ?")
    public void countIllegalCode(){
        List<TableSplit> tableSplits = initService.getCurrentTableSplit();
        for(TableSplit tableSplit:tableSplits){
            asyncService.asyncIllegalCodeTask(tableSplit.getTableName(),tableSplit.getTableType());
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void rejectCodeCount(){
        initService.rejectCount();
    }



    /**
     * 废码统计上传
     * **/
    @Scheduled(cron = "0 10 2 * * ?")
    //@RequestMapping("/disCardCodeUpload")
    public void disCardCodeUpload(){
        Map map = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate =simpleDateFormat.format(new Date());
        map.put("currentDate",currentDate);
        List<DiscardCount> discardCounts = discardService.getDisCardCount(map);
        //返回废码合集后续处理待定
        // discardCodes = discardService.getDiscardCodeList(map);
        if(null != discardCounts && discardCounts.size()>0){
            DiscardCount discardCount = discardCounts.get(0);
            //上传成功标识符，默认上传成功
            discardCount.setUploadState(1);
            //插入上传记录
            discardService.insertDiscardCodeRecord(discardCount);
        }else{
            DiscardCount discardCount = new DiscardCount();
            discardCount.setStripCount(0);
            discardCount.setPackageCount(0);
            discardCount.setUploadModel(1);
            discardCount.setCountDate(currentDate);
            discardCount.setUploadState(1);
            discardService.insertDiscardCodeRecord(discardCount);
        }
    }

    /**
     * 编码上传
     * */
    @Scheduled(cron = "0 12 2 * * ?")
    //@PostConstruct
    public void uploadSuccessCode() throws ParseException {
        log.info("***********************编码上传定时任务***************************");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long stamp     = new Date().getTime()-24*60*60*1000L;//减一天的时间
       // Long stamp     = new Date().getTime()-(2*24*60*60*1000);//减一天的时间
        Date countDate =new Date(stamp);
        String currentDate =simpleDateFormat.format(countDate);
        Map paramMap = new HashMap();
        paramMap.put("currentDate",currentDate);
        UploadRecord uploadRecord = uploadRecordService.getUploadRecordCount(paramMap);//获取当天实时表产量信息，获取当日产量（件）
        if(null != uploadRecord){
            uploadRecord.setProduceDate(currentDate);//数据产生日期
            //uploadRecord.setCurrentProduce(String.valueOf(uploadRecord.getTwigItem()/50));//件码数量
            //查询当日有效编码数量，查询3码关联表，根据生产日期 获取3码关联表的表名
            String tableName = UnionTableNamePrefix+tableScheduleTime(simpleDateFormat_1.format(countDate));
            paramMap.clear();
            paramMap.put("tableName",tableName);
            try{
                UploadRecord actual = uploadRecordService.getUploadRecordActualCount(paramMap);
                uploadRecord.setStatistic(actual.getPackTwig()+actual.getTwigItem());//总数据量，此处为同步返回的同步成功数据量，暂时设定为同样的值
                uploadRecord.setPackTwig(actual.getPackTwig());
                uploadRecord.setTwigItem(actual.getTwigItem());
            }catch (Exception e){
                log.error("************************表**************"+tableName+"查询统计数据执行异常，信息为"+e.getMessage());
            }
            uploadRecord.setUploadModel(1);
            uploadRecord.setStatus(0);
            //暂时未接入国家数据同步，默认完成
            //插入执行任务
            uploadRecordService.insertUploadRecord(uploadRecord);
        }else{
            log.error("************************时间**************"+currentDate+"暂无数据");
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
