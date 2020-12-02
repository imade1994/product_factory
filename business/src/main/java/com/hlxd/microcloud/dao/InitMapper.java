package com.hlxd.microcloud.dao;



import com.hlxd.microcloud.vo.InitMachineTimeVo;
import com.hlxd.microcloud.vo.InitTable;
import com.hlxd.microcloud.vo.InitTableSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InitMapper {


    List<InitMachineTimeVo> getInitMachineTime();

    List<InitMachineTimeVo> getInitMachineTimeFromTable();




    void updateMachineTime(InitMachineTimeVo initMachineTimeVo);

    List<InitTableSchedule> getInitTableScheduleFromTable();

    InitTableSchedule getInitTableSchedule(@Param("machineCode")String machineCode);

    void insertTableSchedule(InitTableSchedule initTableSchedule);

    void updateTableSchedule(InitTableSchedule initTableSchedule);

    void createNewTable(@Param("tableName") String tableName);

    void createNewUnionTable(@Param("tableName")String tableName);


    int checkTableExits(@Param("tableName")String tableName);


    String getTableScheduleString(@Param("machineCode")String machineCode,@Param("currentDate")String currentDate);


    void insertRecordTableInit(InitTable initTable);














}
