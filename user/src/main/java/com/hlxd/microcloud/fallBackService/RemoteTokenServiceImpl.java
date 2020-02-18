package com.hlxd.microcloud.fallBackService;

import com.hlxd.microcloud.feign.AuthService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/516:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT cloud
 */
@Component
public class RemoteTokenServiceImpl implements AuthService {

    @Override
    public Map getToken( Map<String, String> parameters) {
        return null;
    }
}
