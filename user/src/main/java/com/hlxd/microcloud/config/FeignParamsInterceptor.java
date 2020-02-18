package com.hlxd.microcloud.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/716:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT cloud
 */
@Component
@Slf4j
public class FeignParamsInterceptor implements RequestInterceptor {

    private final static String authUrl = "/oauth/token";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String url = requestTemplate.url();
        if(url.equals(authUrl)){
            requestTemplate.header("Authorization", "Basic b3V5YWFhOm91eWFhYQ==");
        }

    }
}
