package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.Qrcode;
import com.hlxd.microcloud.vo.RandomCheckDetails;
import com.hlxd.microcloud.vo.RandomCheckRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/310:39
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface QualityCheckMapper {
    /**
     * 质检记录查询
     * */
    List<RandomCheckRecord> getRandomCheckList(Map map);

    /**
     * 获取质检条，或件的详细信息
     * */
    ProCode getCodeDetail(Map map);

    /**
     * 新增质检信息
     * */
    void insertRandomCheck(@Param("vo")RandomCheckRecord randomCheckRecord);


    /**
     * 新增质检详情
     * */
    void insertRandomCheckDetails(List<RandomCheckDetails> randomCheckDetails);


    /**
     * 获取单条码数据
     * */
    ProCode getCodeRelation(@Param("qrCode") String qrCode);


    /**
     * 获取上下级码数据
     * */
    List<ProCode> getCodeList(Map map);






}
