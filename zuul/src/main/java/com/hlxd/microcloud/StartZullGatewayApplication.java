package com.hlxd.microcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
/**
 * @Auth
 *
 *
 * */
@SpringBootApplication
@EnableZuulProxy
@EnableCaching
public class StartZullGatewayApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(StartZullGatewayApplication.class, args);
    }
}
