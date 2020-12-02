package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.vo.CodeUnion;
import com.hlxd.microcloud.vo.ScheduleErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hlxd.microcloud.util.CommonUtil.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/3014:54
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@Component
@Slf4j
public class AsyncService {

    @Autowired
    private InitService initService;

    @Autowired
    private BatchTaskService batchTaskService;



    @Async("AsyncConfigure")
    public void batchInsertByCanal(String itemCode,String produceDate,String machineCode){
        //log.info("**************************进入异步携带参数itemCode"+itemCode+"生产日期"+produceDate+"**********************************");
        Map param = new HashMap();
        param.put("itemCode",itemCode);
        try {
            String tableName = UnionTableNamePrefix+tableScheduleTime(produceDate);
            //log.info("*************关联详情表"+tableName+"**********************************");
            int count = initService.checkTableExits(tableName);
            if(!(count>0)){
                //log.info("*************表"+tableName+"不存在，手动创建！**********************************");
                initService.createNewUnionTable(tableName);
            }
            List<CodeUnion> codeUnions = batchTaskService.getCodeUnionByItemCode(param);
            if (null != codeUnions&&codeUnions.size()>0){
                batchTaskService.BatchInsertCodeUnion(tableName,codeUnions);
                ScheduleErrorCode scheduleErrorCode = new ScheduleErrorCode();
                scheduleErrorCode.setMachineCode(machineCode);
                scheduleErrorCode.setQrCode(itemCode);
                scheduleErrorCode.setRelationDate(produceDate);
                scheduleErrorCode.setTableName(tableName);
                scheduleErrorCode.setExecuteState(1);
                batchTaskService.insertErrorCode(scheduleErrorCode);
                //batchTaskService.deleteCodeFromSystemCode(itemCode);
            }else{
                ScheduleErrorCode scheduleErrorCode = new ScheduleErrorCode();
                scheduleErrorCode.setMachineCode(machineCode);
                scheduleErrorCode.setQrCode(itemCode);
                scheduleErrorCode.setRelationDate(produceDate);
                scheduleErrorCode.setTableName(tableName);
                scheduleErrorCode.setExecuteState(0);
                batchTaskService.insertErrorCode(scheduleErrorCode);
                log.info(LOG_ERROR_PREFIX+"件码****************************"+itemCode+"****************************没有关联包条码!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
