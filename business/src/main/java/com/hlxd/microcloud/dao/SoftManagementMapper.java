package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SoftManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2021/1/1211:19
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Mapper
public interface SoftManagementMapper {



    int insertSoftManagementRecord(SoftManagement softManagement);

    void deleteSoftManagementRecord(@Param("id")int id);

    void updateSoftManagementRecord(@Param("vo")SoftManagement softManagement);


    void batchAddSoftManagementRecordDetails(@Param("addMachineCodes") List<String> machineCodes,@Param("softId")int softId);


    void batchDeleteSoftManagementRecordDetails(@Param("ids")List<Integer> ids);


    List<SoftManagement> getSoftManagement(Map map);








}
