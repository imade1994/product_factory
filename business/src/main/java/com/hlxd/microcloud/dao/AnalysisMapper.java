package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.CountScan;
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



    /**
     * 读码率
     * */
    List<CountScan> getScanRateByMachineCode(Map map);
    /**
     * 读码率
     * */
    List<CountScan> getScanRateByBrand(Map map);

    /**
     * 读码率
     * */
    List<CountScan> getScanRateByDay(Map map);

    /**
     * 关联率
     * */
    List<CountScan> getRelateRateByDay(Map map);

    /**
     * 关联率
     * */
    List<CountScan> getRelateRateByMachineCode(Map map);


    /**
     * 关联率
     * */
    List<CountScan> getRelateRateByBrandId(Map map);


    /**
     * 作业率
     * */
    List<CountScan> getWorkRateByMachineCode(Map map);

    /**
     * 作业率
     * */
    List<CountScan> getWorkRateByDay(Map map);



    /**
     * 作业率
     * */
    List<CountScan> getWorkRateByBrandId(Map map);


    /**
     * 剔除分析
     * */
    List<CountScan> getRejectCount(Map map);

    /**
     * 码段使用率
     * */
    List<CountScan> getCodeUseByDay(Map map);


    /**
     * 码段使用率
     * */
    List<CountScan> getCodeUseByMachine(Map map);






}
