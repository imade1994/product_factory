package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.Brand;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/317:49
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface BrandMapper {

    /**
     * 获取所有牌号
     * */
    List<Brand> getBrandList(Map map);

    /**
     * 更新牌号信息
     * */
    void updateBrand(@Param("vo")Brand brand);


    /**
     * 新增牌号信息
     * */
    void insertBrand(List<Brand> brands);


}
