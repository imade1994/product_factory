package com.hlxd.microcloud.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.vo.KafkaVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
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
public class KafkaConsumerTest implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;
    private static final String GROUPID = "groupB";

    public KafkaConsumerTest(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.12.250:9092");
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
        int messageNo = 1;
        PreparedStatement pre = null;
        Connection connection = null;
        boolean flag =  false;
        System.out.println("---------开始消费---------");
        try {
            for (;;) {
                msgList = consumer.poll(1000);
                if(null!=msgList&&msgList.count()>0){
                    for (ConsumerRecord<String, String> record : msgList) {
                        JSONObject jsonObject = JSONObject.parseObject(record.value());
                        KafkaVo kafkaVo = JSONObject.toJavaObject(jsonObject,KafkaVo.class);
                        String sql = null;
                        try{
                            sql = getSql(kafkaVo);
                            flag = true;
                        }catch (Exception e){
                            log.info("sql拼接异常，消息体内容为"+record.toString());
                            flag = false;
                            log.error(e.getMessage());
                        }
                        log.info(sql);
                        if(null != sql &&sql.length()>0){
                            try{
                                connection= DruidUtil.getConnection();
                                pre = connection.prepareStatement(sql);
                                if(pre.execute()){
                                    log.info(sql);
                                    log.info("数据同步成功！");
                                }
                            } catch (SQLException e) {
                                flag = false;
                                e.printStackTrace();
                            }finally{
                                DruidUtil.close(pre,connection);
                            }
                        }
                        System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
                        /*TopicPartition topicPartition = new TopicPartition(record.topic(),record.partition());
                        OffsetAndMetadata andMetadata = new OffsetAndMetadata(record.offset(),record.leaderEpoch(),"");
                        Map<TopicPartition,OffsetAndMetadata> map = new HashMap<>();
                        map.put(topicPartition,andMetadata);
                        consumer.commitSync(map);*/
                        messageNo++;
                    }
                    /*if(flag){
                        consumer.commitSync();
                    }*/
                }else{
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    public static String getSql(KafkaVo kafkaVo){
        String type  = kafkaVo.getType();
        String sql = "";
        switch (type){
            case "INSERT":
                sql = "insert into "+kafkaVo.getDataBase()+"."+kafkaVo.getTable();
                String value = "";
                String key = "(";
                List<Map<String,String>> keyMaps = kafkaVo.getData();
                for(Map<String,String> keyMap:keyMaps){
                    key = "(";
                    value = value +"(";
                    for(String s:keyMap.keySet()){
                        key = key + s+",";
                        value = value + "'"+keyMap.get(s)+"' ,";
                    }
                    value = value.substring(0,value.length()-1)+") ,";
                }
                sql = sql + key.substring(0,key.length()-1)+") values "+value.substring(0,value.length()-1);
                break;
            case "UPDATE":
                List<Map<String,String>> newMap = kafkaVo.getData();
                List<String> primaryKeys = kafkaVo.getPkNames();
                for(Map<String,String> map:newMap){
                    String temSql = "update "+kafkaVo.getDataBase()+"."+kafkaVo.getTable()+" set ";
                    String whereSql = " where ";
                    for(String s:map.keySet()){
                        if(primaryKeys.contains(s)){
                            whereSql = whereSql + s +"="+"'"+map.get(s)+"' and";
                        }else{
                            temSql = temSql + s+ "=" + "'"+map.get(s)+"' ,";
                        }
                    }
                    sql = temSql.substring(0,temSql.length()-1)+whereSql.substring(0,whereSql.length()-3);
                }
                break;
            case "DELETE":
                sql = "delete from "+kafkaVo.getDataBase()+"."+kafkaVo.getTable();
                List<Map<String,String>> dataMap = kafkaVo.getData();
                List<String> primary = kafkaVo.getPkNames();
                for(Map<String,String> map:dataMap){
                    String whereSql = " where ";
                    for(String s:map.keySet()){
                        if(primary.contains(s)){
                            whereSql = whereSql + s +"="+"'"+map.get(s)+"' and";
                        }
                    }
                    sql = sql+whereSql.substring(0,whereSql.length()-3);
                }
        }
        return sql;

    }
    public static void main(String args[]) {
        KafkaConsumerTest test1 = new KafkaConsumerTest("canal_topic");
        Thread thread1 = new Thread(test1);
        thread1.start();
    }
}
