package com.hlxd.microcloud.util;


import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.schedule.AsyncService;
import com.hlxd.microcloud.schedule.RedisConsumer;
import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.vo.CodeUnion;
import com.hlxd.microcloud.vo.KafkaVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.data.redis.core.RedisTemplate;

import static com.hlxd.microcloud.util.CommonUtil.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/4/716:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Slf4j
public class KafkaConsumerUtil implements Runnable {


    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final RedisTemplate redisTemplate;
    private   InitService initService;
    private   BatchTaskService batchTaskService;
    private AsyncService asyncService;



    public KafkaConsumerUtil(RedisConsumer redisConsumer) {
        Properties props = new Properties();
        Map configMap = new HashMap();
        //configMap.put("0",AbstractKafkaStatic.partition);
        configMap.put("group.id",AbstractKafkaStatic.groupId);
        configMap.put("bootstrap.servers",AbstractKafkaStatic.servers);
        configMap.put("enable.auto.commit",AbstractKafkaStatic.auto_commit);
        configMap.put("session.timeout.ms",AbstractKafkaStatic.time_out_ms);
        configMap.put("auto.offset.reset",AbstractKafkaStatic.offset_reset);
        configMap.put("key.deserializer",AbstractKafkaStatic.key_series);
        configMap.put("value.deserializer",AbstractKafkaStatic.value_series);
        props.putAll(configMap);
        this.consumer = new KafkaConsumer<String, String>(props);
        this.consumer.subscribe(Arrays.asList(AbstractKafkaStatic.topic));
        this.redisTemplate = redisConsumer.redisTemplate;
        this.initService = redisConsumer.initService;
        this.batchTaskService = redisConsumer.batchTaskService;
        this.asyncService = redisConsumer.asyncService;
    }

    @Override
    public void run() {
        Connection mysqlConnect = null;
        Statement statement = null;
        try {
            mysqlConnect = DruidUtil.getConnection();
            statement    = mysqlConnect.createStatement();
        }catch (Exception e){
            log.info("*********************获取数据库链接失败！******************************");
        }
        //InfluxDB influxDB = InfluxDbUtil.influxDb;
        boolean flag =  false;
        Map<String,String> map = new HashMap();
        System.out.println("---------开始消费---------");
        try {
            for (;;) {
                msgList = consumer.poll(1000);
                log.info("***********************拉取到数据"+msgList.count());
                if(null!=msgList&&msgList.count()>0){
                    for (ConsumerRecord<String, String> record : msgList) {
                        JSONObject jsonObject = JSONObject.parseObject(record.value());
                        KafkaVo kafkaVo = JSONObject.toJavaObject(jsonObject,KafkaVo.class);
                        map = getCode(kafkaVo);
                        String sql = getSql(kafkaVo);
                       if(null != sql && sql.length()>5){
                           try {
                               int rows =statement.executeUpdate(sql);
                               log.info("*************************消费数据插入"+rows+"行************************");
                           }catch (Exception e){
                               log.info("*********************插入新表失败"+e.getMessage());
                           }
                       }
                        for(String s:map.keySet()){
                            try {
                                    redisTemplate.opsForValue().set(s,map.get(s));
                                    flag = true;
                            }catch (Exception e){
                                log.error("redis插入异常********************"+e.getMessage());
                                flag = false;
                            }
                        }
                        if(flag){
                            consumer.commitSync();
                        }
                    }
                }else{
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException | ParseException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
            try {
                mysqlConnect.close();
            }catch (Exception e){
                log.info("******************************连接关闭失败*************************"+e.getMessage());
            }
        }
    }

    public Map getCode(KafkaVo kafkaVo){
        Map map= new HashMap();
        String qrCode = "";
        String machineCode = "";
        String produceDate = "";
        String packageType = "";
        String relationDate= "";
        String type  = kafkaVo.getType();
        if(type.equals("INSERT")){
            List<Map<String,String>> keyMaps = kafkaVo.getData();
            for(Map<String,String> keyMap:keyMaps){
                for(String s:keyMap.keySet()){
                    switch (s){
                        case "qrCode":
                            qrCode = keyMap.get(s);
                            break;
                        case "machine_code":
                            machineCode= " machineCode="+keyMap.get(s);
                            break;
                        case "relation_date":
                            produceDate = " relationDate="+keyMap.get(s);
                            relationDate = keyMap.get(s);
                            break;
                        case "package_type":
                            packageType = keyMap.get(s);
                            break;
                        default:
                            break;
                    }
                }
                if(packageType.equals("1")||packageType.equals("2")){
                    map.put(qrCode,machineCode+produceDate);
                }else if(packageType.equals("3")){
                    log.info("*********************************查询到3的码"+qrCode+"*****************************************");
                    map.put(qrCode,machineCode+produceDate);
                    asyncService.batchInsertByCanal(qrCode,relationDate);
                }
            }
        }
        return map;
    }

    public String getSql(KafkaVo kafkaVo) throws ParseException {
        String type  = kafkaVo.getType();
        String sql = "";
        switch (type){
            case "INSERT":
                if(kafkaVo.getTable().equals("t_hl_system_code")){
                    String machineCode= "";
                    String relationDate="";
                    String value = "";
                    String key = "(";
                    List<Map<String,String>> keyMaps = kafkaVo.getData();
                    for(Map<String,String> keyMap:keyMaps){
                        key = "(";
                        value = value +"(";
                        for(String s:keyMap.keySet()){
                            key = key + s+",";
                            value = value + "'"+keyMap.get(s)+"' ,";
                            switch (s){
                                case "machine_code":
                                    machineCode = keyMap.get(s);
                                    break;
                                case "relation_date":
                                    relationDate = keyMap.get(s);
                                    break;
                                default:
                                    break;
                            }
                        }
                        value = value.substring(0,value.length()-1)+") ,";
                    }
                    log.info("*****************当前数据关联时间为"+relationDate);
                    String tableName = TableNamePrefix+tableScheduleTime(relationDate)+"_"+machineCode;
                    int count = initService.checkTableExits(tableName);

                    if(!(count>0)){
                        log.info("*************表"+tableName+"不存在，手动创建！**********************************");
                        initService.createNewTable(tableName);
                    }
                    sql = "insert into "+kafkaVo.getDataBase()+"."+tableName;
                    sql = sql + key.substring(0,key.length()-1)+") values "+value.substring(0,value.length()-1);
                    log.info("转化后的sql为**************************"+sql+"*******************************************");
                }
                break;
            default:
                break;
        }
        return sql;

    }

   /* public static void main(String args[]) {
        KafkaConsumerUtil test1 = new KafkaConsumerUtil("qrcode_t_hl_illegal_code");
        Thread thread1 = new Thread(test1);
        thread1.start();
    }*/
}
