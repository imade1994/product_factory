package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2215:05
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class MaterialConsume implements Serializable {
    /**
     * 主键
     * */
    private String id;
    /**
     * 材料编码
     * */
    private String materialCode;
    /**
     * 材料名
     * */
    private String materialName;
    /**
     * 配盘数量
     * */
    private String logisticsNumber;
    /**
     * 单位
     *
     * */
    private String unitName;

    /**
     * 实际用量
     * */
    private String doUse;
    /**
     * 实际用量单位
     * */
    private String doUnitName;
    /**
     * 备注
     * */
    private String remark;

    private String batchNumber;

    /**
     * 辅料消耗详情
     * */
    private List<MaterialConsumeDetails> materialConsumeDetails;

}
