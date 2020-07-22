package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.BasicEquipmentMapper;
import com.hlxd.microcloud.service.BasicEquipmentService;
import com.hlxd.microcloud.vo.BasicEquipment;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BasicEquipmentServiceImpl implements BasicEquipmentService {

    @Autowired
    BasicEquipmentMapper basicEquipmentMapper;





    @Override
    public List<BasicEquipment> getBasicEquipment(Map map) {
        return basicEquipmentMapper.getBasicEquipment(map);
    }

    @Override
    public void deleteBasicEquipment(Map map) {
        basicEquipmentMapper.deleteBasicEquipment(map);

    }

    @Override
    public void updateBasicEquipment(Map map) {
        basicEquipmentMapper.updateBasicEquipment(map);
    }

    @Override
    public void insertBasicEquipment(BasicEquipment basicEquipments) {
        basicEquipmentMapper.insertBasicEquipment(basicEquipments);
    }

    @Override
    public void insertBatchBasicEquipment(List<BasicEquipment> basicEquipments) {
        basicEquipmentMapper.insertBatchBasicEquipment(basicEquipments);
    }
}
