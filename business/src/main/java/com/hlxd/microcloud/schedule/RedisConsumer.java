package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.util.KafkaConsumerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
    private  RedisTemplate<String, Object> redisTemplate;

    public static RedisConsumer redisConsumer;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private InitService initService;


    //@PostConstruct
    public void init(){
        redisConsumer = this;
        redisConsumer.redisTemplate = this.redisTemplate;
        redisConsumer.asyncService = this.asyncService;
        redisConsumer.initService = this.initService;
        initDataToRedis();
    }


    public void initDataToRedis(){
        KafkaConsumerUtil test1 = new KafkaConsumerUtil(redisTemplate,asyncService,initService);
        Thread thread1 = new Thread(test1);
        thread1.start();
    }





}
