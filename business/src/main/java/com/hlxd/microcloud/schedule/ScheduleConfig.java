package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import com.hlxd.microcloud.vo.CodeCount;
import com.hlxd.microcloud.vo.CodeRelation;
import com.hlxd.microcloud.vo.ProCode;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

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

    /** influx 连接工厂 */
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://134.175.90.151:8086");

    public static final String dataBase = "hlxd";

    public static final String timestamp = "timestamp";


    @Scheduled(cron = "0 0 0 * * ?")
    @RequestMapping("/upload")
    public static void upload() throws ParseException, InstantiationException, IllegalAccessException {
        //influxDb.enableBatch()
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000L;
       // //Query query = new Query("select * from code  where time >="+beginDate+"000000"+" and time <="+endDate+"000000"+" group by type",dataBase);
        Query query = new Query("select * from code where time>="+beginDate+"000000"+" and time <="+endDate+"000000"+" and type !='3'  group by type,parentCode",dataBase);
        //Query query = new Query("select * from code where  type !='3'  group by type,parentCode",dataBase);
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
        long endDate = new Date().getTime();
        long beginDate = endDate-24*60*60*1000L;
        //Query query = new Query("select count(*) from code  where time >"+beginDate.getTime()+"000000"+" and time <="+endDate.getTime()+"000000"+" group by time(8h),type",dataBase);
        Query query = new Query("select count(*) from code  where time >"+beginDate+"000000"+" and time <="+endDate+"000000"+"  group by time(8h),type,machineCode",dataBase);
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




    @PostConstruct
    public static void initRedis() throws ParseException {
        Jedis jedis = JedisPoolUtils.getJedis();
        long endDate = new Date().getTime();
        long timeBreak = 40*24*60*60*1000L;
        long beginDate = endDate - timeBreak;
        Query query = new Query("select qrCode,remark from code where time >="+beginDate+"000000"+" and time <= "+endDate+"000000 ; select last(*) from code",dataBase);
        QueryResult queryResult = influxDb.query(query);
        if(null != queryResult && null != queryResult.getResults()){
            for(QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        boolean timeFlag =false;
                        boolean initFlag =false;
                        List<String> tags = serie.getColumns();
                        for(String s:tags){
                            switch (s){
                                case "last_remark":
                                    timeFlag = true;
                                    break;
                                case "remark":
                                    initFlag = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(timeFlag){
                            List<Object> strings = serie.getValues().get(0);
                            beginDate = UTCToLong(String.valueOf(strings.get(0)));
                            jedis.set(timestamp,String.valueOf(beginDate));//
                        }
                        if(initFlag){
                            List<List<Object>> keys = serie.getValues();
                            for(List<Object> list:keys){
                                jedis.set(String.valueOf(list.get(1)),String.valueOf(list.get(1)));
                            }
                        }
                    }
                }
            }
        }
        if(null!=jedis){
            jedis.close();
        }
    }
    @PostConstruct
    public void initCode(){
        log.info("生成码开始*******************************************************************");
        String prifix = "HTTPS://TB2D.CN/01/";
        String endfix = "/21/01234567895";
        CopyOnWriteArrayList<String> jian = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> tiao = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> bao = new CopyOnWriteArrayList<>();
        influxDb.enableBatch(10000,10000, TimeUnit.SECONDS);
        influxDb.enableGzip();
        Pong pong =influxDb.ping();
        if(pong==null){
            System.out.println("数据库连接异常");
        }else{
            System.out.println("连接成功，ping值为"+pong.getResponseTime());
        }
        BatchPoints batchPoints = BatchPoints.database(dataBase)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        do{
            String uuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+endfix;
            if(!bao.contains(uuid)){
                //System.out.println(uuid);
                log.info("包码**********************************************"+uuid);
                bao.add(uuid);
            }
        }while(bao.size()<182500000);//包码
        do{
            String uuid = prifix+1+UUID.randomUUID().toString().replaceAll("-","")+endfix;
            if(!tiao.contains(uuid)){
                //System.out.println(uuid);
                log.info("条码**********************************************"+uuid);
                tiao.add(uuid);
            }
        }while(tiao.size()<182500000);//条码
        do{
            String uuid = prifix+2+UUID.randomUUID().toString().replaceAll("-","")+endfix;
            if(!jian.contains(uuid)){
                // System.out.println(uuid);
                log.info("件码**********************************************"+uuid);
                jian.add(uuid);
            }
        }while(jian.size()<3650000);//件码
        Map map = new HashMap();
        for(int i = 0;i<365;i++){//30天
            long day = new Date().getTime();
            day = day-i*24*60*60*1000;
            Map newMap = new HashMap();
            List<String> baoList = bao.subList(i*5000000,(i+1)*5000000);
            List<String> tiaoList = tiao.subList(i*500000,(i+1)*500000);
            List<String> jianList = jian.subList(i*10000,(i+1)*10);
            newMap.put(1,baoList);
            newMap.put(2,tiaoList);
            newMap.put(3,jianList);
            map.put(day,newMap);
        }
        Long jTime=null;
        Long bTime = null;
        int ck = 0;
        for(Object object:map.keySet()){
            log.info("第"+ck+"次循环");
            jTime = Long.valueOf(String.valueOf(object));
            bTime = Long.valueOf(String.valueOf(object));
            Map tem = (Map) map.get(object);
            List<String> jianList = (List<String>) tem.get(3);
            List<String> tiaoList = (List<String>) tem.get(2);
            List<String> baoList = (List<String>) tem.get(1);
            for(int i=0;i<jianList.size();i++){
                List<String> temTiaoList = tiaoList.subList(i*50,(i+1)*50);
                for(int k=0;k<temTiaoList.size();k++){
                    List<String> temBaoList = baoList.subList((50*i+k)*10,(50*i+k+1)*10);
                    for(String s:temBaoList){
                        Point point= Point.measurement("code")
                                .tag("qrCode",s)
                                .tag("type","1")
                                .tag("parentCode",temTiaoList.get(k))
                                .tag("machineCode","G18")
                                .tag("productId","云烟(软珍)")
                                .tag("verifyStatus","0")
                                .tag("factoryName","红河卷烟厂")
                                .field("remark","测试第"+500*i+k+"条烟")
                                .time(bTime*1000000,TimeUnit.NANOSECONDS)
                                .build();
                        batchPoints.point(point);
                    }
                    Point point= Point.measurement("code")
                            .tag("qrCode",temTiaoList.get(k))
                            .tag("type","2")
                            .tag("parentCode",jianList.get(i))
                            .tag("machineCode","G1")
                            .tag("productId","云烟(软珍)")
                            .tag("verifyStatus","0")
                            .tag("factoryName","红河卷烟厂")
                            .field("remark","测试第"+500*i+k+"条烟")
                            .time(bTime*1000000,TimeUnit.NANOSECONDS)
                            .build();
                    batchPoints.point(point);
                    bTime = bTime +1;
                }
                Point point= Point.measurement("code")
                        .tag("qrCode",jianList.get(i))
                        .tag("type","3")
                        .tag("machineCode","G1")
                        .tag("productId","云烟(软珍)")
                        .tag("verifyStatus","0")
                        .tag("factoryName","红河卷烟厂")
                        .field("remark","测试第"+i+1+"件烟")
                        .time(jTime*1000000,TimeUnit.NANOSECONDS)
                        .build();
                jTime = jTime + 500;
                batchPoints.point(point);
            }
        }
        influxDb.write(batchPoints);
    }



    @Scheduled(cron = "0 */5 * * * ?")
    public static void RedisTask() throws ParseException {
        Jedis jedis = JedisPoolUtils.getJedis();
        long endDate = new Date().getTime();
        long beginDate = 0;
        if(jedis.exists(timestamp)){
            beginDate = Long.parseLong(jedis.get(timestamp));
        }else{
            long timeBreak = 15*24*60*60*1000L;
            beginDate = endDate - timeBreak;
        }
        Query query = new Query("select qrCode,remark from code where time >="+beginDate+" and time <= "+endDate,dataBase);
        QueryResult queryResult = influxDb.query(query);
        if(null != queryResult && null != queryResult.getResults()){
            for(QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        List<List<Object>> keys = serie.getValues();
                        for(List<Object> list:keys){
                            if(!jedis.exists(String.valueOf(list.get(1)))){
                                jedis.set(String.valueOf(list.get(1)),String.valueOf(list.get(1)));
                            }
                        }
                    }
                }
            }
        }
        jedis.set(timestamp,String.valueOf(endDate));
        if(null!=jedis){
            jedis.close();
        }
    }

}
