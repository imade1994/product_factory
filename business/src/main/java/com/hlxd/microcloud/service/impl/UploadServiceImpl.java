package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.UploadRecordMapper;
import com.hlxd.microcloud.service.UploadRecordService;
import com.hlxd.microcloud.vo.UploadRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2810:40
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Service
public class UploadServiceImpl implements UploadRecordService {
    @Autowired
    private UploadRecordMapper uploadRecordMapper;


    @Override
    public List<UploadRecord> getUploadRecord(Map map) {
        return uploadRecordMapper.getUploadRecord(map);
    }

    @Override
    public void updateUploadRecord(Map map) {
        uploadRecordMapper.updateUploadRecord(map);
    }

    @Override
    public void insertUploadRecord(UploadRecord uploadRecord) {
        uploadRecordMapper.insertUploadRecord(uploadRecord);
    }

    @Override
    public UploadRecord getUploadRecordCount(Map map) {
        return uploadRecordMapper.getUploadRecordCount(map);
    }

    @Override
    public UploadRecord getUploadRecordActualCount(Map map) {
        return uploadRecordMapper.getUploadRecordActualCount(map);
    }
}
