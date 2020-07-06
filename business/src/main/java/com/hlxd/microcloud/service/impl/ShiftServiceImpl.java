package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.ShiftMapper;
import com.hlxd.microcloud.service.ShiftService;
import com.hlxd.microcloud.vo.Shift;
import com.hlxd.microcloud.vo.ShiftDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/316:21
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftMapper shiftMapper;


    @Override
    public List<Shift> getShift(Map map) {
        return shiftMapper.getShift(map);
    }

    @Override
    public void updateShift(Shift shift) {
        shiftMapper.updateShift(shift);
    }

    @Override
    public void insertShift(Shift shift) {
        shiftMapper.insertShift(shift);
    }

    @Override
    public int validateParent(Map map) {
        return shiftMapper.validateParent(map);
    }

    @Override
    public void updateShiftDetails(ShiftDetails shiftDetails) {
        shiftMapper.updateShiftDetails(shiftDetails);
    }

    @Override
    public void deleteShiftDetails(ShiftDetails shiftDetails) {
        shiftMapper.deleteShiftDetails(shiftDetails);
    }

    @Override
    public void insertShiftDetails(ShiftDetails shiftDetails) {
        shiftMapper.insertShiftDetails(shiftDetails);
    }
}
