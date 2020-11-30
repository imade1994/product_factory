package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.BatchTaskMapper;
import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.vo.CodeUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/10/2317:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class BatchTaskServiceImpl implements BatchTaskService {
    @Autowired
    private BatchTaskMapper batchTaskMapper;




    @Override
    public List<String> getPackageCodeUnion() {
        return batchTaskMapper.getPackageCodeUnion();
    }

    @Override
    public List<CodeUnion> getCodeUnion(Map map) {
        return batchTaskMapper.getCodeUnion(map);
    }

    @Override
    public void BatchInsertCodeUnion(String tableName,List<CodeUnion> codeUnions) {
        batchTaskMapper.BatchInsertCodeUnion(tableName,codeUnions);
    }

    @Override
    public List<CodeUnion> getCodeUnionByItemCode(Map map) {
        return batchTaskMapper.getCodeUnionByItemCode(map);
    }

    @Override
    public void deleteCodeFromSystemCode(String itemCode) {
        batchTaskMapper.deleteCodeFromSystemCode(itemCode);
    }

}
