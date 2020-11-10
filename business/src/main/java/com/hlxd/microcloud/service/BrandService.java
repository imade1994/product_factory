package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.Brand;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/715:13
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface BrandService {


    /**
     * 获取所有牌号
     * */
    List<Brand> getBrandList(Map map);

    /**
     * 更新牌号信息
     * */
    void updateBrand(Brand brand);


    /**
     * 新增牌号信息
     * */
    void insertBrand(List<Brand> brands);

    /**
     * 删除牌号
     * */
    void deleteBrand(String id);


    /**
     * 获取牌号信息
     * */
    Brand getBrandByCode(String code);

}
