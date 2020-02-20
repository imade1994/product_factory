package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.VerificationMapper;
import com.hlxd.microcloud.entity.Verification;
import com.hlxd.microcloud.service.IVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2914:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class VerificationServiceImpl implements IVerificationService {
    @Autowired
    private VerificationMapper verificationMapper;
    @Override
    public List<Verification> getVerification(Map map) {
        return verificationMapper.getVerification(map);
    }
}
