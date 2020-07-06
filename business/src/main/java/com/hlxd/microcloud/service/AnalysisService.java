package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.RejectCount;
import com.hlxd.microcloud.vo.ScanCount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1210:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface AnalysisService {

    /**
     * 获取统计数据
     *
     * */
    List<ScanCount> getCountStatic(Map map);

    /**
     * 获取机台的启动状态
     * */
    List<Machine> getMachineStatus(Map map);

    List<ScanCount> getScanCount(Map map);

    void batchInsertScanCount(List<ScanCount> scanCounts);

    void batchInsertRejectCount(List<RejectCount> rejectCounts);
}
