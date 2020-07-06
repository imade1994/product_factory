package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1114:19
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class ScanCount implements Serializable {


    /**
     *实时扫描数
     * */
    private int realtimeScan;

    /**
     *实时扫描剔除数
     * */
    private int realtimeScanReject;

    /**
     *实时关联数
     * */
    private int realtimeRelate;

    /**
     *实时关联剔除数
     * */
    private int realtimeReject;

    /**
     *机台名
     * */
    private String machineName;


    /**
     *品牌名，牌号
     * */
    private String brandName;


    /**
     *工厂名
     * */
    private String factoryName;

    /**
     *类型 1：包装机 2：装箱机
     * */
    private int type;

    /**
     *随机抽检数
     * */
    private int randomCheck;

    /**
     *随机抽检失败数
     * */
    private int randomCheckFail;


    /**
     * 数采产量
     *
     * */
    private int collectionCount;

    /**
     * 工作效率
     * */
    private float workEfficiency;

    /**
     * 关联效率
     * */
    private float scanEfficiency;
    /**
     *关联停机次数
     * */
    private int relateTimeBreakCount;

    /**
     *关联停机总时长
     * */
    private int relateTimeBreakTime;

    /**
     *班次班组 1：早班  2：中班  3：晚班
     * */
    private int period;

    /**
     *日期 年月日
     * format YYYY-mm-DD
     * */
    private String time;

    /**
     *记录备注
     * */
    private String remark;


    /**
     * 剔除详情
     * */
    private List<RejectCount> rejectCounts;

    /**
     *
     * 停机详情
     * */
    private List<BreakRecord> breakRecords;

    /**
     * 抽检详情
     * 批次抽检
     * */
    private List<CheckRecord> checkRecords;


    /**
     * 质量抽检
     * */
    private List<RandomCheckRecord> randomCheckRecords;

    /**
     * 日期数据统计
     * 不区分机台 只区分时间，包装机或者装箱机
     * */
    private int relateCount;

    /**
     * 剔除统计数据
     *
     * */
    private int relateReject;

    /**
     * 读码率
     * */
    private String scanRate;

    /**
     * 停机总时间
     * */
    private int breakOutCount;

    /**
     *
     * */

    /**
     * 各机台统计数据
     * */
    private List<ScanCount> scanCounts;





}
