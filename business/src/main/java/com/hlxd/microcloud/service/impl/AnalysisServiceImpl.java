package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.AnalysisMapper;
import com.hlxd.microcloud.service.AnalysisService;
import com.hlxd.microcloud.vo.CountScan;
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

    @Override
    public List<CountScan> getScanRateByMachineCode(Map map) {
        return analysisMapper.getScanRateByMachineCode(map);
    }

    @Override
    public List<CountScan> getScanRateByBrand(Map map) {
        return analysisMapper.getScanRateByBrand(map);
    }

    @Override
    public List<CountScan> getScanRateByDay(Map map) {
        return analysisMapper.getScanRateByDay(map);
    }

    @Override
    public List<CountScan> getRelateRateByDay(Map map) {
        return analysisMapper.getRelateRateByDay(map);
    }

    @Override
    public List<CountScan> getRelateRateByMachineCode(Map map) {
        return analysisMapper.getRelateRateByMachineCode(map);
    }

    @Override
    public List<CountScan> getRelateRateByBrandId(Map map) {
        return analysisMapper.getRelateRateByBrandId(map);
    }

    @Override
    public List<CountScan> getWorkRateByMachineCode(Map map) {
        return analysisMapper.getWorkRateByMachineCode(map);
    }

    @Override
    public List<CountScan> getWorkRateByDay(Map map) {
        return analysisMapper.getWorkRateByDay(map);
    }

    @Override
    public List<CountScan> getWorkRateByBrandId(Map map) {
        return analysisMapper.getWorkRateByBrandId(map);
    }

    @Override
    public List<CountScan> getRejectCount(Map map) {
        return analysisMapper.getRejectCount(map);
    }

    @Override
    public List<CountScan> getCodeUseByDay(Map map) {
        return analysisMapper.getCodeUseByDay(map);
    }

    @Override
    public List<CountScan> getCodeUseByMachine(Map map) {
        return analysisMapper.getCodeUseByMachine(map);
    }
}
