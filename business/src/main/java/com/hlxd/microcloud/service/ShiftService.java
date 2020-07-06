package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.Shift;
import com.hlxd.microcloud.vo.ShiftDetails;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/316:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Data
public interface ShiftService {

    /**
     * 获取班组及详情
     * */
    List<Shift> getShift(Map map);


    /**
     * 更新班组信息
     * */
    void updateShift( Shift shift);

    /**
     * 新建班组
     * */
    void insertShift(Shift shift);

    /**
     * 验证是否存在上级
     * */
    int validateParent(Map map);

    /**
     * 更新组详情
     * */
    void updateShiftDetails( ShiftDetails shiftDetails);

    /**
     * 删除子分组
     * */
    void deleteShiftDetails(ShiftDetails shiftDetails);

    /**
     * 新建分组
     * */
    void insertShiftDetails(ShiftDetails shiftDetails);


}
