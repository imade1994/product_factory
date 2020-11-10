package com.hlxd.microcloud.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class InitMachineTimeVo implements Serializable {



    /**
     * 设备编码
     * */
    private String machineCode;


    /**
     * 当前班组
     * */
    private int shiftId;


    /**
     * 当前班组开始时间
     * */
    private String beginDate;

    /**
     *
     * 当前班组结束时间
     * */
    private String endDate;

    /**
     * 当前班组名
     * */
    private String className;


}
