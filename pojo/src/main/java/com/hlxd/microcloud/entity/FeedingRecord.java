package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/2214:53
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Data
public class FeedingRecord implements Serializable {

    private String id;

    private String throwingNumber;

    private String startFeedingDate;

    private String endFeedingDate;

    private String equipmentCode;

    private String feedingEquipmentCode;

    private String remark;

    private String organizeCode;


}
