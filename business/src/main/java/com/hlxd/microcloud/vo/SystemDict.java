package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2510:22
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class SystemDict implements Serializable {
     /**
      * id
      * */
     private String id;

     /**
      * 父类ID
      * */
     private String parentId;



     /**
      * 属性名 key 值
      * */
     private String key;

     /**
      * 属性值 value值
      * */
     private String value;

     /**
      * 最后更新时间
      * */
     private String lastUpdateDate;

     /**
      * 创建日期
      * */
     private String createDate;

     /**
      * 创建人
      * */
     private String createPeople;


     /**
      * 启用状态
      * */
     private int status;


     /**
      * 子菜单集合
      * */
     private List<SystemDict> systemDicts;


}
