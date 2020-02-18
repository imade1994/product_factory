package com.hlxd.microcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/***
 * Spring Eureka 服务注册中心
 * @author hualong117
 * @date 2018/05/02
 */
@SpringBootApplication
@EnableEurekaServer
public class StartServerApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(StartServerApplication.class, args);
    }
}