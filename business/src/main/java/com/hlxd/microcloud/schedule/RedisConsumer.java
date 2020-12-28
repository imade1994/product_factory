package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.util.KafkaConsumerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/1617:17
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Component
@Slf4j
public class RedisConsumer {

    @Autowired
    public  RedisTemplate<String, Object> redisTemplate;

    public  RedisConsumer redisConsumer;

    @Autowired
    public BatchTaskService batchTaskService;

    @Autowired
    public InitService initService;

    @Autowired
    public AsyncService asyncService;


    @PostConstruct
    public void init(){
        redisConsumer = this;
        redisConsumer.redisTemplate = this.redisTemplate;
        redisConsumer.batchTaskService = this.batchTaskService;
        redisConsumer.initService = this.initService;
        redisConsumer.asyncService = this.asyncService;
        //initDataToRedis();
    }


    public void initDataToRedis(){
        KafkaConsumerUtil consumer = new KafkaConsumerUtil(redisConsumer);
        Thread thread = new Thread(consumer);
        thread.start();
    }





}
