package com.hlxd.microcloud.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CodeCheckVo implements Serializable {

    /**
     * 条码
     * */
    private String qrCode;


    /**
     * 机台名
     * */
    private String machineName;


    /**
     * 生产时间
     * */
    private String produceDate;

}
