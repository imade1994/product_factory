package com.hlxd.microcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2115:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableCircuitBreaker
@MapperScan("com.hlxd.microcloud.dao")
//@EnableFeignClients
@EnableScheduling
public class BusinessApplication {
  public static void main(String[] args) {
      SpringApplication.run(BusinessApplication.class,args);
  }
}
