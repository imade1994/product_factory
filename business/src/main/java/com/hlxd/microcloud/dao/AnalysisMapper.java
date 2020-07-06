package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.RejectCount;
import com.hlxd.microcloud.vo.ScanCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1114:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface AnalysisMapper {

    /**
     * 根据条件筛选实时数据
     *
     * */
    List<ScanCount> getScanCount(Map map);


    /**
     * 获取统计数据
     *
     * */
    List<ScanCount> getCountStatic(Map map);


    /**
     * 获取机台的启动状态
     * */
    List<Machine> getMachineStatus(Map map);

    void batchInsertScanCount(List<ScanCount> scanCounts);

    void batchInsertRejectCount(List<RejectCount> rejectCounts);




}
