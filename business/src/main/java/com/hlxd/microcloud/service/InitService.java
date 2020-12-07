package com.hlxd.microcloud.service;


import com.hlxd.microcloud.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InitService {

    List<InitMachineTimeVo> getInitMachineTime();

    List<InitMachineTimeVo> getInitMachineTimeFromTable();

    void insertTableSchedule(InitTableSchedule initTableSchedule);


    void updateMachineTime(InitMachineTimeVo initMachineTimeVo);


    List<InitTableSchedule> getInitTableScheduleFromTable();

    InitTableSchedule getInitTableSchedule(String machineCode);

    void updateTableSchedule(InitTableSchedule initTableSchedule);


    void createNewTable(String tableName);

    void createNewUnionTable(String tableName);

    int checkTableExits(String tableName);

    String getTableScheduleString(String machineCode,String currentDate);


    void insertRecordTableInit(InitTable initTable);


    ProCode getProCode(String tableName,String qrCode);


    ProCode getProCodes(Map map);


    List<TableSplit> getTableSplit(Map map);


    ProductionCount getCountFromUnion(Map map);

    List<TableSplit> getCurrentTableSplit();

    void countIllegalCode(String tableName);

    int countCode(Map map);



}
