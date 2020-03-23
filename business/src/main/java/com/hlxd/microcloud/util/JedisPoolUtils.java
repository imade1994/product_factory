package com.hlxd.microcloud.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/6/289:27
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT zcjt-cloud
 */
public class JedisPoolUtils {

    private static JedisPool pool = null;

    private static final  int maxActive = 300 ;

    private static final  int maxIdle = 100;

    private static final  int maxWait = 1000;

    private static final String host = "127.0.0.1";

    private static final Integer port = 6379;

    static{//获取连接池对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxActive);//最大闲置个数
        poolConfig.setMinIdle(maxIdle);//最小闲置个数
        poolConfig.setMaxTotal(maxWait);//最大连接数
        pool = new JedisPool(host,port);
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }
    //存值：
    public static void setPool(Integer dataBase,String key,String value){
        Jedis jedis = getJedis();
        jedis.select(dataBase);
        jedis.set(key,value);
        jedis.close();
    }
    //取值：
    public static String getPool(Integer dataBase,String key){
        Jedis jedis = getJedis();
        jedis.select(dataBase);
        String s =jedis.get(key);
        jedis.close();
        return s ;
    }

    //删除值
    public static void delValue(Integer dataBase,String key, List<String> list){
        Jedis jedis = getJedis();
        jedis.select(dataBase);
        for (String i :list ) {
            jedis.lrem(key,0,i);
        }
        jedis.close();
    }
    public static void delAllValue(){
        Jedis jedis = getJedis();
        //jedis.select(dataBase);
        jedis.flushAll();
    }

}
