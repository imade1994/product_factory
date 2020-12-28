package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.UploadRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/12/2810:40
 * @VERSION 4.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Service
public interface UploadRecordService {


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
     * 获取实际同步数量
     * **/
    UploadRecord getUploadRecordActualCount(Map map);
}
