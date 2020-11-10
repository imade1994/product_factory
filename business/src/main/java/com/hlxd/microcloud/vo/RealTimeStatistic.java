package com.hlxd.microcloud.vo;

import java.util.List;
import java.util.Objects;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/9/2416:04
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public class RealTimeStatistic {



    /**
     * 机台
     * */
    private String machineCode;


    /**
     * 编号
     * */
    private String machineName;

    /**
     * 当前班组
     * */
    private String currentShiftId;

    /**
     * 当前班组名
     * */
    private String currentClassName;


    /**
     * 统计数据
     * */
    private List<ScanCount> scanCounts;

    public String getCurrentShiftId() {
        return currentShiftId;
    }

    public void setCurrentShiftId(String currentShiftId) {
        this.currentShiftId = currentShiftId;
    }

    public String getCurrentClassName() {
        return currentClassName;
    }

    public void setCurrentClassName(String currentClassName) {
        this.currentClassName = currentClassName;
    }

    public List<ScanCount> getScanCounts() {
        return scanCounts;
    }

    public void setScanCounts(List<ScanCount> scanCounts) {
        this.scanCounts = scanCounts;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealTimeStatistic that = (RealTimeStatistic) o;
        return machineCode.equals(that.machineCode) &&
                machineName.equals(that.machineName) &&
                currentShiftId.equals(that.currentShiftId) &&
                currentClassName.equals(that.currentClassName) &&
                scanCounts.equals(that.scanCounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineCode, machineName, currentShiftId, currentClassName, scanCounts);
    }
}
