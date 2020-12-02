package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.schedule.AsyncService;
import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.vo.CodeUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/2716:45
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private AsyncService asyncService;

    @RequestMapping("init")
    public void initTables(){
        Map map = new HashMap();
        map.put("itemCode","Fail0311030582020-09-18 17:46:23");
        //asyncService.batchInsertByCanal("Fail0311030582020-09-18 17:46:23","2020-09-18 17:46:23");
    }
}
