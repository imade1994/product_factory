package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MaterConsyme implements Serializable {
    private String id;

    private String materialCode;

    private BigDecimal logisticsNumber;

    private String unitName;

    private BigDecimal doUse;

    private String doUnitName;

    private String remark;

    private String batchNumber;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public BigDecimal getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(BigDecimal logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getDoUse() {
        return doUse;
    }

    public void setDoUse(BigDecimal doUse) {
        this.doUse = doUse;
    }

    public String getDoUnitName() {
        return doUnitName;
    }

    public void setDoUnitName(String doUnitName) {
        this.doUnitName = doUnitName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}