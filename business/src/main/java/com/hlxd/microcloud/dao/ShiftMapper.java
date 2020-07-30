package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.Shift;
import com.hlxd.microcloud.vo.ShiftDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/314:42
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface ShiftMapper {



    /**
     * 获取班组及详情
     * */
    List<Shift> getShift(Map map);


    /**
     * 删除班组
     * */
    void deleteShift(@Param("id")String id);


    /**
     * 更新班组信息
     * */
    void updateShift(@Param("vo") Shift shift);

    /**
     * 新建班组
     * */
    void insertShift(@Param("vo")Shift shift);

    /**
     * 验证是否存在上级
     * */
    int validateParent(Map map);

    /**
     * 校验是否存在下级
     * */
    int validateChildren(@Param("id")String id);

    /**
     * 更新组详情
     * */
    void updateShiftDetails(@Param("vo")ShiftDetails shiftDetails);

    /**
     * 删除子分组
     * */
    void deleteShiftDetails(@Param("vo")ShiftDetails shiftDetails);

    /**
     * 新建分组
     * */
    void insertShiftDetails(@Param("vo")ShiftDetails shiftDetails);

    /**
     * 验证班组是否存在
     * */
    int validateExist(@Param("shiftName")String shiftName);


}
