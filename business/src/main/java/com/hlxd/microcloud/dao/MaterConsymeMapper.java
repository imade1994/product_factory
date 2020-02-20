package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.entity.MaterConsyme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterConsymeMapper {

    List<MaterConsyme> selectByMaterCode(@Param("materialCode") String materialCode);

}