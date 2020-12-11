package com.hlxd.microcloud.dao;



import com.hlxd.microcloud.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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


    ProCode getProCode(@Param("tableName")String tableName,@Param("qrCode")String qrCode);


    ProCode getProCodes(Map map);


    List<TableSplit> getTableSplit(Map map);

    List<TableSplit> getCurrentTableSplit();

    ProductionCount getCountFromUnion(Map map);


    void countIllegalCode(@Param("tableName")String tableName);


    int countCode(Map map);


    void rejectCount();

}
