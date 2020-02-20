package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.Verification;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2913:55
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public interface IVerificationService {

    List<Verification> getVerification(Map map);
}
