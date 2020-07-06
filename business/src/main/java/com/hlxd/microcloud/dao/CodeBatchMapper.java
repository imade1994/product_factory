package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.CodeBatch;
import com.hlxd.microcloud.vo.CodeDetail;
import com.hlxd.microcloud.vo.UploadRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1817:01
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Mapper
public interface CodeBatchMapper {


    /**
     * 查询批次信息
     *
     * */
    List<CodeBatch> getBatch(Map map);

    /**
     * 根据条件查询码的信息
     *
     * */
    List<CodeDetail> getCodeDetail(Map map);

    /**
     * 新建批次信息
     * */
    void insertBatch(@Param("vo") CodeBatch codeBatch);


    /**
     * 新建分表
     * */
    void createNewTable(@Param("tableName") String tableName);


    /**
     * 查询同步任务
     * */
    List<UploadRecord> getUploadRecord(@Param("produceDate")String produceDate);


    /**
     * 插入任务处理结果
     * */
    void insertNewUploadRecord(@Param("vo")UploadRecord uploadRecord);

    /**
     * 更新任务处理结果
     * */
    void updateUploadRecord(@Param("id")int id);

    /**
     * 验证是否为失败任务
     * */
    int validateUploadRecord(@Param("id")int id);



}
