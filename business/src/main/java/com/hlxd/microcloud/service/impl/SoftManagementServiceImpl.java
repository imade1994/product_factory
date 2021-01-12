package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SoftManagementMapper;
import com.hlxd.microcloud.service.SoftManagementService;
import com.hlxd.microcloud.vo.SoftManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1211:27
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Service
public class SoftManagementServiceImpl implements SoftManagementService {

    @Autowired
    private SoftManagementMapper softManagementMapper;


    @Override
    public void insertSoftManagementRecord(SoftManagement softManagement) {
        softManagementMapper.insertSoftManagementRecord(softManagement);
    }

    @Override
    public void deleteSoftManagementRecord(int id) {
        softManagementMapper.deleteSoftManagementRecord(id);
    }

    @Override
    public void updateSoftManagementRecord(SoftManagement softManagement) {
        softManagementMapper.updateSoftManagementRecord(softManagement);
    }

    @Override
    public void batchAddSoftManagementRecordDetails(List<String> machineCodes, int softId) {
        softManagementMapper.batchAddSoftManagementRecordDetails(machineCodes, softId);

    }

    @Override
    public void batchDeleteSoftManagementRecordDetails(List<Integer> ids) {
        softManagementMapper.batchDeleteSoftManagementRecordDetails(ids);

    }
}
