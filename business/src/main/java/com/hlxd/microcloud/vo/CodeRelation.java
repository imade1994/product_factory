package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/3/39:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */@Data
public class CodeRelation  implements Serializable {


     //包码时间
     private String time;
     //包二维码
     private String qrCode;
     //条二维码
     private String baoCode;
     //件二维码
     private String jianCode;
     //品牌，牌号
     private String brandName;
     //装箱机台号
     private String jMachineCode;
     //卷包机台号
     private String machineCode;
     //装箱备注
     private String jRemark;
     //卷包备注
     private String bRemark;
     //箱激活状态
     private String jVerifyStatus;
     //条激活状态
     private String bVerifyStatus;
     //装箱时间
     private String jProduceDate;
     //卷包时间
     private String bProduceDate;

     //filed 做过滤的冗余字段
     private String jQrCode;

}
