package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.MachineService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.Machine;
import com.hlxd.microcloud.vo.SoftMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.transformMap;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/414:36
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/machine")
public class MachineController {
    @Autowired
    private MachineService machineService;



    @RequestMapping("/getAllMachine")
    public Map getAllMachine(HttpServletRequest request){
        Map returnMap = new HashMap();
        Map paramMap = transformMap(request.getParameterMap());
        List<Machine> machines = machineService.getMachineList(paramMap);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,machines);
        return returnMap;
    }

    @RequestMapping("/getSoftMachine")
    public Map getSoftMachine(){
        Map returnMap = new HashMap();
        List<SoftMachine> softMachines = machineService.getAllSoftMachines();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,softMachines);
        return returnMap;
    }



    /**
     * 更新机台信息
     * 机组，车间，状态
     * */
    @RequestMapping("/updateMachineInformation")
    public Map updateMachineInformation(Machine machine){
        Map returnMap = new HashMap();
        if(null == machine){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }else{
            if(null == machine.getMachineCode()){
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
            }else{
                machineService.updateMachine(machine);
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
            }
        }
        return returnMap;
    }


    /**
     * 添加设备
     *
     * */
    @RequestMapping("/addMachine")
    public Map addMachine(Machine machines){
        Map returnMap = new HashMap();
        if(null == machines){
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.lack_Params);
        }else{
            machineService.insertMachine(machines);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }
        return returnMap;
    }

    /**
     * 删除机台
     * */
    @RequestMapping("/deleteMachine")
    public Map deleteMachine(@RequestParam("machineCode")String machineCode){
        Map returnMap = new HashMap();
        machineService.deleteMachine(machineCode);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    /**
     * 车间下拉菜单
     * */
    @RequestMapping("/getRoom")
    public Map getRoom(){
        Map returnMap = new HashMap();
        List<String>  list = machineService.getRoom();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,list);
        return returnMap;

    }





}
