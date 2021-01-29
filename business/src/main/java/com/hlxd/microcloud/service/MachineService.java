package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.SoftMachine;
import org.apache.ibatis.annotations.Param;
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
public interface MachineService {

    /**
     * 查询机台信息
     *
     * */
    List<Machine> getMachineList(Map maps);


    /**
     * 更新机台信息
     * */
    void updateMachine(Machine machine);


    /**
     * 新增机台
     * */
    void insertMachine(Machine machines);



    /**
     * 删除机台
     * */
    void deleteMachine(String machineCode);

    /**
     * 车间下拉
     * */
    List<String> getRoom();

    Machine getMachine(String machineCode);



    /**
     * 适配机器
     * */
    List<SoftMachine> getAllSoftMachines(Map map);
}
