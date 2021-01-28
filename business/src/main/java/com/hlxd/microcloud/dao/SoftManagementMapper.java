package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.SoftVo;
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


    /**
     * 新增软件信息
     * */
    void insertSoft(SoftVo soft);

    /**
     * 更新软件信息
     * */
    void updateSoft(Map map);

    /**
     * 删除软件
     * */
    void deleteSoft(@Param("id")int id);

    /**
     * 新增版本信息*
     * */
    int insertSoftManagementRecord(SoftManagement softManagement);
    /**
     * 删除版本
     * */
    void deleteSoftManagementRecord(@Param("id")int id);

    /**
     * 获取版本信息
     * */
    SoftManagement getSoftVersion(@Param("id")int id);


    /**
     * 更新版本信息
     * */
    void updateSoftManagementRecord(@Param("vo")SoftManagement softManagement);

    /**
     * 批量更新适应版本信息
     * */
    void batchAddSoftManagementRecordDetails(@Param("addMachineCodes") List<String> machineCodes,@Param("softId")int softId);

    /**
     * 批量删除适应版本信息
     * */
    void batchDeleteSoftManagementRecordDetails(@Param("ids")List<Integer> ids);

    /**
     * 查询软件及版本信息
     * */
    List<SoftVo> getSoftManagement(Map map);


}
