package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.AnalysisMapper;
import com.hlxd.microcloud.service.AnalysisService;
import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.RejectCount;
import com.hlxd.microcloud.vo.ScanCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1210:20
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
     AnalysisMapper analysisMapper;


    @Override
    public List<ScanCount> getCountStatic(Map map) {
        return analysisMapper.getCountStatic(map);
    }

    @Override
    public List<Machine> getMachineStatus(Map map) {
        return analysisMapper.getMachineStatus(map);
    }

    @Override
    public List<ScanCount> getScanCount(Map map) {
        return analysisMapper.getScanCount(map);
    }

    @Override
    public void batchInsertScanCount(List<ScanCount> scanCounts) {
        analysisMapper.batchInsertScanCount(scanCounts);
    }

    @Override
    public void batchInsertRejectCount(List<RejectCount> rejectCounts) {
        analysisMapper.batchInsertRejectCount(rejectCounts);

    }
}
