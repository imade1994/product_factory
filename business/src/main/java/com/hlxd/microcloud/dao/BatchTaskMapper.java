package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.CodeUnion;
import com.hlxd.microcloud.vo.ScheduleErrorCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/10/2316:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface BatchTaskMapper {

    List<String> getPackageCodeUnion();

    List<CodeUnion> getCodeUnion(Map map);

    void BatchInsertCodeUnion(@Param("tableName")String tableName,@Param("list") List<CodeUnion> codeUnions);

    List<CodeUnion> getCodeUnionByItemCode(Map map);


    void deleteCodeFromSystemCode(@Param("itemCode") String itemCode);


    void insertErrorCode(ScheduleErrorCode scheduleErrorCode);

    List<ScheduleErrorCode> getErrorCode(@Param("executeState")int executeState );


    void deleteSchedule(@Param("id")int id);

    void updateSchedule(@Param("id")int id,@Param("executeState")int executeState);



}
