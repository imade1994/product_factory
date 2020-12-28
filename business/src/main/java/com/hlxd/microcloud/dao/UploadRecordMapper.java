package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.UploadRecord;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2810:38
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Mapper
public interface UploadRecordMapper {


    /**
     * 查询上传任务
     * */
    List<UploadRecord> getUploadRecord(Map map);

    /**
     * 更新上传任务
     *
     * */
    void updateUploadRecord(Map map);


    /**
     * 新增上传任务
     * */
    void insertUploadRecord(UploadRecord uploadRecord);


    /**
     * 获取编码当日生产总量
     * */
    UploadRecord getUploadRecordCount(Map map);


    /**
     * 获取有效编码数量
     * **/
    UploadRecord getUploadRecordActualCount(Map map);
}
