package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.ProCode;
import com.hlxd.microcloud.vo.RandomCheckDetails;
import com.hlxd.microcloud.vo.RandomCheckRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/7/310:53
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public interface QualityCheckService {


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
    void insertRandomCheck(RandomCheckRecord randomCheckRecord);


    /**
     * 新增质检详情
     * */
    void insertRandomCheckDetails(List<RandomCheckDetails> randomCheckDetails);

}
