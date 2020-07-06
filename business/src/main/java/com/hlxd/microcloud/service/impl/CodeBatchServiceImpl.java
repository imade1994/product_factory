package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.CodeBatchMapper;
import com.hlxd.microcloud.service.CodeBatchService;
import com.hlxd.microcloud.vo.CodeBatch;
import com.hlxd.microcloud.vo.CodeDetail;
import com.hlxd.microcloud.vo.UploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1911:05
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class CodeBatchServiceImpl implements CodeBatchService {

    @Autowired
    CodeBatchMapper codeBatchMapper;



    @Override
    public List<CodeBatch> getCodeBatch(Map map) {
        return codeBatchMapper.getBatch(map);
    }

    @Override
    public List<CodeDetail> getCodeDetail(Map map) {
        return codeBatchMapper.getCodeDetail(map);
    }

    @Override
    public void insertBatch(CodeBatch codeBatch) {
        codeBatchMapper.insertBatch(codeBatch);
    }

    @Override
    public void createNewTable(String tableName) {
        codeBatchMapper.createNewTable(tableName);
    }

    @Override
    public List<UploadRecord> getUploadRecord(String produceDate) {
        return codeBatchMapper.getUploadRecord(produceDate);
    }

    @Override
    public void insertNewUploadRecord(UploadRecord uploadRecord) {
        codeBatchMapper.insertNewUploadRecord(uploadRecord);
    }

    @Override
    public void updateUploadRecord(int id) {
        codeBatchMapper.updateUploadRecord(id);
    }

    @Override
    public int validateUploadRecord(int id) {
        return codeBatchMapper.validateUploadRecord(id);
    }
}
