package com.hlxd.microcloud.util;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/4/710:16
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public class AbstractKafkaTest  extends BaseCanalClientTest {
    public static String  topic     = "canal_topic";
    public static Integer partition = null;
    public static String  groupId   = "g4";
    public static String  servers   = "192.168.12.250:9092";
    public static String  zkServers = "192.168.12.250:2181";

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
