package com.hlxd.microcloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 * 权限维护服务
 * @author hualong117
 * @date 2018/05/04
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan("com.hlxd.microcloud.dao")
@EnableScheduling
@EnableFeignClients
@EnableCaching
public class StartUserServerApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(StartUserServerApplication.class, args);
    }

}
