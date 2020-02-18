package com.hlxd.oauthorization;


import com.hlxd.oauthorization.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;







@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableConfigurationProperties(SecurityProperties.class)
@EnableFeignClients
@EnableCaching
public class AuthenticationServer_Application {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(AuthenticationServer_Application.class,args);
    }
}