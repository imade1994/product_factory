package com.hlxd.microcloud.service.impl;


import com.hlxd.microcloud.dao.InitMapper;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class InitServiceImpl implements InitService {

    @Autowired
    InitMapper initMapper;

    @Override
    public List<InitMachineTimeVo> getInitMachineTime() {
        return initMapper.getInitMachineTime();
    }

    @Override
    public List<InitMachineTimeVo> getInitMachineTimeFromTable() {
        return initMapper.getInitMachineTimeFromTable();
    }

    @Override
    public void insertTableSchedule(InitTableSchedule initTableSchedule) {
        initMapper.insertTableSchedule(initTableSchedule);
    }

    @Override
    public void updateMachineTime(InitMachineTimeVo initMachineTimeVo) {
        initMapper.updateMachineTime(initMachineTimeVo);

    }

    @Override
    public List<InitTableSchedule> getInitTableScheduleFromTable() {
        return initMapper.getInitTableScheduleFromTable();
    }

    @Override
    public InitTableSchedule getInitTableSchedule(String machineCode) {
        return initMapper.getInitTableSchedule(machineCode);
    }

    @Override
    public void updateTableSchedule(InitTableSchedule initTableSchedule) {
        initMapper.updateTableSchedule(initTableSchedule);

    }

    @Override
    public void createNewTable(String tableName) {
        initMapper.createNewTable(tableName);
    }

    @Override
    public void createNewUnionTable(String tableName) {
       initMapper.createNewUnionTable(tableName);
    }

    @Override
    public int checkTableExits(String tableName) {
        return initMapper.checkTableExits(tableName);
    }

    @Override
    public String getTableScheduleString(String machineCode, String currentDate) {
        return initMapper.getTableScheduleString(machineCode,currentDate);
    }

    @Override
    public void insertRecordTableInit(InitTable initTable) {
        initMapper.insertRecordTableInit(initTable);
    }

    @Override
    public ProCode getProCode(String tableName,String qrCode) {
        return initMapper.getProCode(tableName,qrCode);
    }

    @Override
    public ProCode getProCodes(Map map) {
        return initMapper.getProCodes(map);
    }

    @Override
    public List<TableSplit> getTableSplit(Map map) {
        return initMapper.getTableSplit(map);
    }
}
