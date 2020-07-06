package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.Machine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/6/414:04
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface MachineMapper {

    /**
     * 查询机台信息
     *
     * */
    List<Machine> getMachineList(Map maps);


    /**
     * 更新机台信息
     * */
    void updateMachine(@Param("vo") Machine machine);


    /**
     * 新增机台
     * */
    void insertMachine(List<Machine> machines);
}
