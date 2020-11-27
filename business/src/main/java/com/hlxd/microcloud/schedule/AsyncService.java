package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.vo.CodeUnion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/2410:35
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Service
@Slf4j
public class AsyncService {

    @Autowired
    private BatchTaskService batchTaskService;

    @Autowired
    private InitService initService;



    @Async
    public void batchInsertByCanal(String itemCode,String produceDate){
        Map param = new HashMap();
        param.put("itemCode",itemCode);
        try {
            String tableName = UnionTableNamePrefix+tableScheduleTime(produceDate);
            int count = initService.checkTableExits(tableName);
            if(!(count>0)){
                initService.createNewUnionTable(tableName);
            }
            List<CodeUnion> codeUnions = batchTaskService.getCodeUnionByItemCode(param);
            if (null != codeUnions&&codeUnions.size()>0){
                batchTaskService.BatchInsertCodeUnion(tableName,codeUnions);
            }else{
                log.info("件码****************************"+itemCode+"****************************没有关联包条码!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
