package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.Blending;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 * 投 料
 * @Author taojun
 * @Date 2019/11/2116:32
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Mapper
public interface BlendingMapper {

    List<Blending> getBlending(Map  map);

}
