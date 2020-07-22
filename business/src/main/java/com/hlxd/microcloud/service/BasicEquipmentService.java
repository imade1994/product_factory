package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.BasicEquipment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/1615:03
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface BasicEquipmentService {

    /**
     * 查询所有设备
     * */
    List<BasicEquipment> getBasicEquipment(Map map);

    /**
     *
     * 删除设备
     * */
    void deleteBasicEquipment(Map map);

    /**
     *
     *更新设备
     * */
    void updateBasicEquipment(Map map);

    /**
     * 新增设备
     *
     * */
    void insertBasicEquipment(BasicEquipment basicEquipments);


    /**
     * 新增设备
     *  批量
     * */
    void insertBatchBasicEquipment(List<BasicEquipment> basicEquipments);



}
