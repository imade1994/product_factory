package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.CodeMapper;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.service.ICodeService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2115:57
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Service
public class CodeServiceImpl implements ICodeService {

    @Autowired(required = false)
    private CodeMapper codeMapper;

    @Override
    public ProCode getCodeDetails(Map map) {
        return codeMapper.getCodeDetails(map);
    }

    @Override
    public List<ProCode> getCodeByWrap(String machineCode, String doBeginDate, String doEndDate) {
        return codeMapper.getCodeByWrap(machineCode,doBeginDate,doEndDate);
    }

    @Override
    public List<ProCode> getCodeByTime(String startFeedingDate, String endFeedingDate) {
        return codeMapper.getCodeByTime(startFeedingDate,endFeedingDate);
    }
}
