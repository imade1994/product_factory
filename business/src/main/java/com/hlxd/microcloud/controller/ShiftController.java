package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.ShiftService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.util.CommonUtil;
import com.hlxd.microcloud.vo.Shift;
import com.hlxd.microcloud.vo.ShiftDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/317:29
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/api/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;


    @RequestMapping("/getShift")
    public Map getShift(ServletRequest servletRequest){
        Map param = CommonUtil.transformMap(servletRequest.getParameterMap());
        Map returnMap = new HashMap();
        List<Shift> shifts = shiftService.getShift(param);
        returnMap.put(CommomStatic.DATA,shifts);
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        return returnMap;
    }

    @RequestMapping("/insertShift")
    public Map insertShift(Shift shift){
        shiftService.insertShift(shift);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/updateShift")
    public Map updateShift(Shift shift){
        shiftService.updateShift(shift);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/insertShiftDetails")
    public Map insertShiftDetails(ShiftDetails shiftDetails){
        Map param = new HashMap();
        param.put("id",shiftDetails.getShiftId());
        Map returnMap = new HashMap();
        int count = shiftService.validateParent(param);
        if(count>0){
            shiftService.insertShiftDetails(shiftDetails);
            returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
            returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"父级不存在！");
        }
        return returnMap;
    }

    @RequestMapping("updateShiftDetails")
    public Map updateShiftDetails(ShiftDetails shiftDetails){
        shiftService.updateShiftDetails(shiftDetails);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }

    @RequestMapping("/deleteShiftDetails")
    public Map deleteShiftDetails(ShiftDetails shiftDetails){
        shiftService.deleteShiftDetails(shiftDetails);
        Map returnMap = new HashMap();
        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
        return returnMap;
    }







}
