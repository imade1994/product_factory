package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.*;
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
     * 获取条码信息
     * */
    List<CodeUnion> getCodeByParentCode(Map map);

    /**
     * 获取机台的启动状态
     * */
    List<Machine> getMachineStatus(Map map);

    List<RealTimeStatistic> getScanCount(Map map);

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


    /**
     * 读码率详情
     * */
    List<CountScan> getScanRateByDayDetails(Map map);

    /**
     * 读码率详情
     * */
    List<CountScan> getScanRateByMachineDetails(Map map);


    /**
     * 关联率详情
     * */
    List<CountScan> getRelateRateByDayDetails(Map map);


    /**
     * 关联率详情
     * */
    List<CountScan> getRelateRateByMachineDetails(Map map);


    /**
     * 作业率详情
     * */
    List<CountScan> getWorkRateByDayDetails(Map map);



    /**
     * 作业率详情
     * */
    List<CountScan> getWorkRateByMachineDetails(Map map);



    /**
     * 查询烟包或烟条或件码重复关联
     * */
    List<RepeatCount> getRepeatCount(Map map);

    /**
     * 获取单个码的重复关联数据
     * */
    List<ProCode> getRepeatList(Map map);

    /**
     * 查询烟包或烟条或件码重复关联
     * total
     * */
    int getRepeatCountTotal(Map map);


    /**
     * 获取单个码的重复关联数据
     * total
     * */
    int getRepeatListTotal(Map map);



    /**
     * 获取包装机或装封箱机 所有统计数据
     * */
    AllMachineCount getAllMachineCountByGroupCode(String typeCode);


    /**
     * 获取包装机或装封箱机 当前班组数据
     * */
    List<MachineCount> getAllMachineCount(String typeCode);
}
