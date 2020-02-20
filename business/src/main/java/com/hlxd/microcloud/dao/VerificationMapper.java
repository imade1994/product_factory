package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Verification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 质检
 * @Author taojun
 * @Date 2019/11/2116:35
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface VerificationMapper {

    List<Verification> getVerification(Map map);
}
