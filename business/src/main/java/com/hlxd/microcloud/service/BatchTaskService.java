package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.CodeUnion;
import com.hlxd.microcloud.vo.ScheduleErrorCode;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/10/2317:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public interface BatchTaskService {


    List<String> getPackageCodeUnion();

    List<CodeUnion> getCodeUnion(Map map);

    void BatchInsertCodeUnion(String tableName,List<CodeUnion> codeUnions);


    List<CodeUnion> getCodeUnionByItemCode(Map map);

    void deleteCodeFromSystemCode(String itemCode);


    void insertErrorCode(ScheduleErrorCode scheduleErrorCode);


    List<ScheduleErrorCode> getErrorCode(int executeState);


    void deleteSchedule(int id);


    void updateSchedule(int id,int executeState);

    void deleteItemBeforeInsert(String tableName,String itemCode);
}
