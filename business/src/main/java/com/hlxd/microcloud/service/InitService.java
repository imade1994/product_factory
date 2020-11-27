package com.hlxd.microcloud.service;


import com.hlxd.microcloud.vo.InitMachineTimeVo;
import com.hlxd.microcloud.vo.InitTableSchedule;

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



}
