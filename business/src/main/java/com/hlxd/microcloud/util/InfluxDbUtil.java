package com.hlxd.microcloud.util;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import java.util.concurrent.TimeUnit;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/2316:40
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
public class InfluxDbUtil {

    static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100,TimeUnit.SECONDS);

    /** influx 连接工厂 */
    public static final InfluxDB influxDb = InfluxDBFactory.connect("http://192.168.2.8:8086",client);

    public static final String dataBase = "hlxd";

    public static final String timestamp = "timestamp";
}
