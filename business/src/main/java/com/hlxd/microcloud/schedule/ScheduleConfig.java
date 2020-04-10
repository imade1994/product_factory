package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import com.hlxd.microcloud.util.ThreadManager;
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

    /** influx 连接工厂 */
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://192.168.12.250:8086");

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




   // @PostConstruct
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
    //@PostConstruct
    public void initCode() throws ExecutionException, InterruptedException {
        log.info("生成码开始*******************************************************************");
        String prifix = "HTTPS://TB2D.CN/01/";
        String endfix = "/21/01234567895";
        influxDb.enableBatch(10000,10000, TimeUnit.SECONDS);
        influxDb.enableGzip();
        List<FutureTask<Boolean>> ShoppingCartTasks = new ArrayList<>();
        Pong pong =influxDb.ping();
        if(pong==null){
            System.out.println("数据库连接异常");
        }else{
            System.out.println("连接成功，ping值为"+pong.getResponseTime());
        }
        BatchPoints batchPoints = BatchPoints.database(dataBase)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        int count = 1;
        long day = new Date().getTime();
        /*CopyOnWriteArrayList<String> jian = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> tiao = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> bao = new CopyOnWriteArrayList<>();*/
        log.debug("**************************数据模拟开始时间"+new Date().getTime());
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        long bTime = 0;
        long jTime = 0;
        for(int i=0;i<365;i++){
            log.info("****************第"+i+"次循环******************************");
            int finalI = i;
            Callable<Boolean> ShoppingCartDetailsCall = new Callable<Boolean>() {
                @Override
                public Boolean call()  {
                    Map<Integer,CopyOnWriteArrayList<String>> jMap = new HashMap<>();
                    Map<Integer,CopyOnWriteArrayList<String>> tMap = new HashMap<>();
                    Map<Integer,CopyOnWriteArrayList<String>> bMap = new HashMap<>();
                    long btime = day -finalI*24*60*60*1000L;
                    long jtime = day -finalI*24*60*60*1000L;
                    do{
                        String uuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+endfix;
                        if(null != bMap.get(finalI)){
                            if(!bMap.get(finalI).contains(uuid)){
                                //System.out.println(uuid);
                                log.info("****************包码******************************"+uuid);
                                bMap.get(finalI).add(uuid);
                            }
                        }else{
                            CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
                            strings.add(uuid);
                            bMap.put(finalI,strings);
                        }
                    }while(bMap.get(finalI).size()<500000);//包码182500
                    try{
                        do{
                            String uuid = prifix+1+UUID.randomUUID().toString().replaceAll("-","")+endfix;
                            if(null != tMap.get(finalI)){
                                if(!tMap.get(finalI).contains(uuid)){
                                    //System.out.println(uuid);
                                    log.info("****************条码******************************"+uuid);
                                    tMap.get(finalI).add(uuid);
                                }
                            }else{
                                CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
                                strings.add(uuid);
                                tMap.put(finalI,strings);
                            }
                        }while(tMap.get(finalI).size()<50000);//条码18250
                        do{
                            String uuid = prifix+1+UUID.randomUUID().toString().replaceAll("-","")+endfix;
                            if(null != jMap.get(finalI)){
                                if(!jMap.get(finalI).contains(uuid)){
                                    //System.out.println(uuid);
                                    log.info("****************件码******************************"+uuid);
                                    jMap.get(finalI).add(uuid);
                                }
                            }else{
                                CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
                                strings.add(uuid);
                                jMap.put(finalI,strings);
                            }
                        }while(jMap.get(finalI).size()<1000);//件码10000
                    }catch (Exception e){
                        log.debug("**************************发生异常"+e.getMessage());
                        return false;
                    }
                    log.info("****************第"+finalI+"次任务执行完成,开始封装插入******************************");
                    for(int k=0;k<jMap.get(finalI).size();k++){
                        List<String> temTiaoList = tMap.get(finalI).subList(k*50,(k+1)*50);
                        for(int f=0;f<temTiaoList.size();f++){
                            List<String> temBaoList = bMap.get(finalI).subList((50*k+f)*10,(50*k+f+1)*10);
                            for(String s:temBaoList){
                                Point point= Point.measurement("code")
                                        .tag("qrCode",s)
                                        .tag("type","1")
                                        .tag("parentCode",temTiaoList.get(f))
                                        .tag("machineCode","G18")
                                        .tag("productId","云烟(软珍)")
                                        .tag("verifyStatus","0")
                                        .tag("factoryName","红河卷烟厂")
                                        .field("remark","测试第"+500*k+f+"条烟")
                                        .time(btime*1000000L,TimeUnit.NANOSECONDS)
                                        .build();
                                batchPoints.point(point);
                            }
                            Point point= Point.measurement("code")
                                    .tag("qrCode",temTiaoList.get(f))
                                    .tag("type","2")
                                    .tag("parentCode",jMap.get(finalI).get(k))
                                    .tag("machineCode","G1")
                                    .tag("productId","云烟(软珍)")
                                    .tag("verifyStatus","0")
                                    .tag("factoryName","红河卷烟厂")
                                    .field("remark","测试第"+500*k+f+"条烟")
                                    .time(btime*1000000L,TimeUnit.NANOSECONDS)
                                    .build();
                            batchPoints.point(point);
                            btime = btime +1;
                        }
                        Point point= Point.measurement("code")
                                .tag("qrCode",jMap.get(finalI).get(k))
                                .tag("type","3")
                                .tag("machineCode","G1")
                                .tag("productId","云烟(软珍)")
                                .tag("verifyStatus","0")
                                .tag("factoryName","红河卷烟厂")
                                .field("remark","测试第"+k+1+"件烟")
                                .time(jtime*1000000L,TimeUnit.NANOSECONDS)
                                .build();
                        jtime = jtime + 500;
                        batchPoints.point(point);
                        if(batchPoints.getPoints().size()%10000==0&&batchPoints.getPoints().size()!=0){
                            influxDb.write(batchPoints);
                        }
                    }

                    return true;
                }
            };
            FutureTask<Boolean> ShoppingCartTask = new FutureTask<>(ShoppingCartDetailsCall);
            ShoppingCartTasks.add(ShoppingCartTask);
            ThreadManager.getDownloadPool().execute(ShoppingCartTask);

        }


        /*if(ThreadManager.validate(30000)){
            log.debug("**************************进入主线程开始阻塞"+bao.size());
            log.debug("**************************包码数量"+bao.size());
            log.debug("**************************条码数量"+tiao.size());
            log.debug("**************************件码数量"+jian.size());
            Map map = new HashMap();
            for(int i = 0;i<365;i++){//30天
                day = day-i*24*60*60*1000L;
                Map newMap = new HashMap();
                List<String> baoList = bao.subList(i*50000,(i+1)*50000);
                List<String> tiaoList = tiao.subList(i*5000,(i+1)*5000);
                List<String> jianList = jian.subList(i*100,(i+1)*100);
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

            }

        }*/


    }

    //@PostConstruct
    @Scheduled(cron = "0 */5 * * * ?")
    public void initStatic(){
        log.info("生成码开始*******************************************************************");
        String prifix = "HTTPS://TB2D.CN/01/";
        String endfix = "/21/01234567895";
        if(!influxDb.isBatchEnabled()){
            influxDb.enableBatch(10000,10000, TimeUnit.SECONDS);
        }
        if(!influxDb.isGzipEnabled()){
            influxDb.enableGzip();
        }
        List<FutureTask<Boolean>> ShoppingCartTasks = new ArrayList<>();
        Pong pong =influxDb.ping();
        if(pong==null){
            System.out.println("数据库连接异常");
        }else{
            System.out.println("连接成功，ping值为"+pong.getResponseTime());
        }
        BatchPoints batchPoints = BatchPoints.database(dataBase)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        long day = new Date().getTime();
        log.info("**************************数据模拟开始时间"+new Date().getTime());
        long bTime = 0;
        long jTime = 0;
        for(int k=0;k<1;k++){
            log.info("****************第"+k+"次循环******************************");
            int finalI = k;
            /*Callable<Boolean> ShoppingCartDetailsCall = new Callable<Boolean>() {
                @Override
                public Boolean call()  {

                    log.info("**************************数据模拟开始时间"+(new Date().getTime()-day));
                    return true;
                }
            };
            FutureTask<Boolean> ShoppingCartTask = new FutureTask<>(ShoppingCartDetailsCall);
            ShoppingCartTasks.add(ShoppingCartTask);
            ThreadManager.getDownloadPool().execute(ShoppingCartTask);*/
            long btime = day -finalI*24*60*60*1000L;
            long jtime = day -finalI*24*60*60*1000L;
            String tiaouuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+finalI+1+endfix;
            String jianuuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+finalI+2+endfix;
            for(int i=0;i<500000;i++){
                String uuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+finalI+0+endfix;
                if(i%10==0){
                    tiaouuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+finalI+1+endfix;
                    Point point= Point.measurement("code")
                            .tag("qrCode",tiaouuid)
                            .tag("type","2")
                            .tag("parentCode",jianuuid)
                            .tag("machineCode","G1")
                            .tag("productId","云烟(软珍)")
                            .tag("verifyStatus","0")
                            .tag("factoryName","红河卷烟厂")
                            .field("remark","测试第"+i/10+"条烟")
                            .time(btime*1000000L,TimeUnit.NANOSECONDS)
                            .build();
                    batchPoints.point(point);
                    btime = btime +1;
                }
                if(i%500==0){
                    log.info("第+"+finalI+"线程+++++++++++++++++++++++++++++++第"+i/500+"件");
                    jianuuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+finalI+2+endfix;
                    Point point= Point.measurement("code")
                            .tag("qrCode",jianuuid)
                            .tag("type","3")
                            .tag("machineCode","G1")
                            .tag("productId","云烟(软珍)")
                            .tag("verifyStatus","0")
                            .tag("factoryName","红河卷烟厂")
                            .field("remark","测试第"+i/500+"件烟")
                            .time(jtime*1000000L,TimeUnit.NANOSECONDS)
                            .build();
                    batchPoints.point(point);
                    jtime = jtime + 500;
                }
                Point point= Point.measurement("code")
                        .tag("qrCode",uuid)
                        .tag("type","1")
                        .tag("parentCode",tiaouuid)
                        .tag("machineCode","G18")
                        .tag("productId","云烟(软珍)")
                        .tag("verifyStatus","0")
                        .tag("factoryName","红河卷烟厂")
                        .field("remark","测试第"+i+"包烟")
                        .time(btime*1000000L,TimeUnit.NANOSECONDS)
                        .build();
                batchPoints.point(point);
                /*if(batchPoints.getPoints().size()%10000==0&&batchPoints.getPoints().size()!=0){

                }*/
            }
            influxDb.write(batchPoints);


        }
        influxDb.close();





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
