package com.hlxd.microcloud.util;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/4/710:16
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public class AbstractKafkaStatic extends BaseCanalClientTest {
    public static String  topic         = "qrcode_t_hl_system_code";
    public static Integer partition     = null;
    public static String  groupId       = "hlxd_09";
    public static String  servers       = "10.165.45.245:9092";
    public static String  zkServers     = "10.165.45.245:2181";
    public static boolean auto_commit   = false;
    public static Integer time_out_ms   = 600000;
    public static String  offset_reset  = "earliest";
    public static String  key_series      =  StringDeserializer.class.getName();
    public static String  value_series      =  StringDeserializer.class.getName();



    private static final String GROUPID = "group1";


    public static final Map<String,Object> configMap = new HashMap<>();


    static{
        //configMap.put("topic",topic);
        configMap.put("0",partition);
        configMap.put("group.id",groupId);
        configMap.put("bootstrap.servers",servers);
        configMap.put("enable.auto.commit",auto_commit);
        configMap.put("session.timeout.ms",time_out_ms);
        configMap.put("auto.offset.reset",offset_reset);
        configMap.put("key.deserializer",key_series);
        configMap.put("value.deserializer",value_series);
    }

}
