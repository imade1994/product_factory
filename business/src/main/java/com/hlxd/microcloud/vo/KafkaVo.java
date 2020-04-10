package com.hlxd.microcloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/4/717:10
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public class KafkaVo implements Serializable {

    private List<Map<String,String>> data;

    private String dataBase;

    private long es;

    private int id;

    private boolean isDdl;

    private Map mysqlType;

    private List<Map<String,String>> old;

    private List<String> pkNames;

    private String sql;

    private Map sqlType;

    private String table;

    private long ts;

    private String type;
}
