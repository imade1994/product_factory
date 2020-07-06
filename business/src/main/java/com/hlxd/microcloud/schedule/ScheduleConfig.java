package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.AnalysisService;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.ThreadManager;
import com.hlxd.microcloud.vo.CodeCount;
import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.RejectCount;
import com.hlxd.microcloud.vo.ScanCount;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.ParseException;
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
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://127.0.0.1:8086",client);

    public static final String dataBase = "hlxd";

    public static final String timestamp = "timestamp";

    public static ScheduleConfig scheduleConfig;



    @Autowired
    AnalysisService analysisService;

    @PostConstruct
    public void init(){
        scheduleConfig = this;
        scheduleConfig.analysisService = this.analysisService;
    }


    @Scheduled(cron = "0 0 0 * * ?")
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

    @PostConstruct
    public static boolean initUpload(){
        String str  = CommonUtil.getFormateString();
        uploadStatistic(str);
        return true;
    }


}
