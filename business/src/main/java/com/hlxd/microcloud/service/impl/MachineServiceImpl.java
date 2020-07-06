package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.MachineMapper;
import com.hlxd.microcloud.service.MachineService;
import com.hlxd.microcloud.vo.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/414:08
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    MachineMapper machineMapper;

    @Override
    public List<Machine> getMachineList(Map maps) {
        return machineMapper.getMachineList(maps);
    }

    @Override
    public void updateMachine(Machine machine) {
        machineMapper.updateMachine(machine);
    }

    @Override
    public void insertMachine(List<Machine> machines) {
        machineMapper.insertMachine(machines);
    }


}
