package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.BrandMapper;
import com.hlxd.microcloud.service.BrandService;
import com.hlxd.microcloud.vo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;


    @Override
    public List<Brand> getBrandList(Map map) {
        return brandMapper.getBrandList(map);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateBrand(brand);
    }

    @Override
    public void insertBrand(List<Brand> brands) {
        brandMapper.insertBrand(brands);
    }

    @Override
    public void deleteBrand(String id) {
        brandMapper.deleteBrand(id);
    }

    @Override
    public Brand getBrandByCode(String code) {
        return brandMapper.getBrandByCode(code);
    }
}
