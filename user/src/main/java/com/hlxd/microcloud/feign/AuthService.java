package com.hlxd.microcloud.feign;

import com.hlxd.microcloud.fallBackService.RemoteTokenServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/516:22
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT cloud
 */
@FeignClient(name = "auth-server",fallback = RemoteTokenServiceImpl.class)
public interface AuthService {

    @RequestMapping(value = "/oauth/token",method = RequestMethod.POST)
    public Map getToken(@RequestParam Map<String, String> parameters);


}
