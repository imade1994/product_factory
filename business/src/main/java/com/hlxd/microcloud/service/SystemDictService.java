package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.SystemDict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2514:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface SystemDictService {

    /**
     * 查询数据字典
     * */
    List<SystemDict> getDictList(Map map);

    /**
     * 新增数据字典
     * */
    Map insertSystemDict(SystemDict systemDict);

    /**
     * 更新数据字典
     * */
    void updateSystemDict(SystemDict systemDict);

    /**
     * 移除数据字典
     * */
    Map deleteSystemDict(Map map);

    /**
     * 查询数据字典 全量查询
     * */
    List<SystemDict> getAllDictList(Map map);
}
