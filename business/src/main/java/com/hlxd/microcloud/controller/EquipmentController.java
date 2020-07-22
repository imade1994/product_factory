package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BasicEquipmentService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.BasicEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1614:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    BasicEquipmentService basicEquipmentService;



    @RequestMapping("/getList")
    public Map getList(ServletRequest request){
        Map returnMap = new HashMap();
        List<BasicEquipment> basicEquipments = basicEquipmentService.getBasicEquipment(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.DATA,basicEquipments);
        return returnMap;
    }


    @RequestMapping("/updateEquipment")
    public Map updateEquipment(ServletRequest request){
        Map returnMap = new HashMap();
        basicEquipmentService.updateBasicEquipment(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }


    @RequestMapping("/deleteEquipment")
    public Map deleteEquipment(ServletRequest request){
        Map returnMap = new HashMap();
        basicEquipmentService.deleteBasicEquipment(CommonUtil.transformMap(request.getParameterMap()));
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }


    @RequestMapping("/batchInsertEquipment")
    public Map insertBatchBasicEquipment(List<BasicEquipment> basicEquipments){
        Map returnMap = new HashMap();
        basicEquipmentService.insertBatchBasicEquipment(basicEquipments);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/InsertEquipment")
    public Map batchInsertEquipment(BasicEquipment basicEquipments){
        Map returnMap = new HashMap();
        basicEquipmentService.insertBasicEquipment(basicEquipments);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }






















}
