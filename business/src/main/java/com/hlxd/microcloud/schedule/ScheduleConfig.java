package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.util.CommonUtil;
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

import java.util.*;

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
    public static void upload(){
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000;
        Query query = new Query("select * from code  where time >="+beginDate+"000000"+" and time <="+endDate+"000000"+" group by type",dataBase);
        QueryResult queryResult = influxDb.query(query);
        Map map = new HashMap<>();
        if(null != queryResult && null != queryResult.getResults()){
           for(QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        List<ProCode> proCodes = new ArrayList<>();
                        List<String> columns = serie.getColumns();
                        List<List<Object>> values = serie.getValues();
                        proCodes.addAll(CommonUtil.getQueryData(columns, values));
                        map.put(serie.getTags().get("type"),proCodes);
                    }
                }
           }
        }
        List<ProCode> jianCode = (List<ProCode>) map.get("3");
        List<ProCode> tiaoCode = (List<ProCode>) map.get("2");
        List<ProCode> baoCode = (List<ProCode>) map.get("1");

    }

}
