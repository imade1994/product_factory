package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.CodeBatch;
import com.hlxd.microcloud.vo.CodeDetail;
import com.hlxd.microcloud.vo.UploadRecord;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1911:03
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public interface CodeBatchService {

    /**
     * 获取所有批次信息
     *
     * */
    List<CodeBatch> getCodeBatch(Map map);

    /**
     * 根据条件查询码的信息
     *
     * */
    List<CodeDetail> getCodeDetail(Map map);

    /**
     * 新建批次信息
     * */
    void insertBatch(CodeBatch codeBatch);

    /**
     * 新建分表
     * */
    void createNewTable(String tableName);

    /**
     * 查询同步任务
     * */
    List<UploadRecord> getUploadRecord(String produceDate);


    /**
     * 插入任务处理结果
     * */
    void insertNewUploadRecord(UploadRecord uploadRecord);

    /**
     * 更新任务处理结果
     * */
    void updateUploadRecord(int id);

    /**
     * 验证是否为失败任务
     * */
    int validateUploadRecord(int id);
}
