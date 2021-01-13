package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.SoftManagement;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1211:27
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
public interface SoftManagementService {

    int insertSoftManagementRecord(SoftManagement softManagement);

    void deleteSoftManagementRecord(int id);

    void updateSoftManagementRecord(SoftManagement softManagement);

    void batchAddSoftManagementRecordDetails(List<String> machineCodes,int softId);


    void batchDeleteSoftManagementRecordDetails(List<Integer> ids);


    List<SoftManagement> getSoftManagement(Map map);



}
