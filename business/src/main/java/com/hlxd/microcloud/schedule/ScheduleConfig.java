package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.CodeCount;
import com.hlxd.microcloud.vo.CodeRelation;
import com.hlxd.microcloud.vo.ProCode;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

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
public class ScheduleConfig {

    /** influx 连接工厂 */
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://134.175.90.151:8086");

    public static final String dataBase = "hlxd";


    @Scheduled(cron = "0 0 0 * * ?")
    @RequestMapping("/upload")
    public static void upload() throws ParseException, InstantiationException, IllegalAccessException {
        //influxDb.enableBatch()
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000;
        //Query query = new Query("select * from code  where time >="+beginDate+"000000"+" and time <="+endDate+"000000"+" group by type",dataBase);
        Query query = new Query("select * from code where type !='3'  group by type,parentCode",dataBase);
        QueryResult queryResult = influxDb.query(query);
        Map<String,Map<String,List<ProCode>>> map = new HashMap<>();
        if(null != queryResult && null != queryResult.getResults()){
           for(QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
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

    }


    @Scheduled(cron = "0 0 0 * * ?")
    @RequestMapping("/count")
    public static void count() throws ParseException, InstantiationException, IllegalAccessException {
        //influxDb.enableBatch()
        Date beginDate = new Date();
        //date.setYear(2020);
        beginDate.setMonth(beginDate.getMonth()-1);
        beginDate.setDate(29);
        beginDate.setMinutes(0);
        beginDate.setHours(0);
        beginDate.setSeconds(0);
        Date endDate = new Date();
        endDate.setMonth(endDate.getMonth()-1);
        endDate.setDate(29);
        endDate.setMinutes(0);
        endDate.setHours(24);
        endDate.setSeconds(0);

        //long endDate = new Date().getTime();
        //long beginDate = date.getTime()-24*60*60*1000;
        //Query query = new Query("select count(*) from code  where time >"+beginDate.getTime()+"000000"+" and time <="+endDate.getTime()+"000000"+" group by time(8h),type",dataBase);
        Query query = new Query("select count(*) from code  group by time(8h),type,machineCode",dataBase);
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
    }



}
