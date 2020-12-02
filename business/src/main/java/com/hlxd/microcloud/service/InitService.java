package com.hlxd.microcloud.service;


import com.hlxd.microcloud.vo.InitMachineTimeVo;
import com.hlxd.microcloud.vo.InitTable;
import com.hlxd.microcloud.vo.InitTableSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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



}
