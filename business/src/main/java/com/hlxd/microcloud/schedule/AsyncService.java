package com.hlxd.microcloud.schedule;

import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.service.IllegalCodeService;
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

import static com.hlxd.microcloud.util.CommomStatic.REDIS_UNION_CODE_LOCK;
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


    @Autowired
    private IllegalCodeService illegalCodeService;

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;



    @Async("AsyncConfigure")
    public void batchInsertByCanal(String itemCode,String produceDate,String machineCode){
        log.info("**************************进入异步携带参数itemCode"+itemCode+"生产日期"+produceDate+"**********************************");
        Map param = new HashMap();
        param.put("itemCode",itemCode);
        String lock = (String)redisTemplate.opsForValue().get(REDIS_UNION_CODE_LOCK+itemCode);
        if(null == lock){
            /**
             * 放入redis锁
             * 保证其他线程不能操作这条数据
             * */
            redisTemplate.opsForValue().set(REDIS_UNION_CODE_LOCK+itemCode,1);
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
                    /**
                     * 先删除，再执行插入
                     * */
                    batchTaskService.deleteItemBeforeInsert(tableName,itemCode);
                    batchTaskService.BatchInsertCodeUnion(tableName,codeUnions);
                    /* */
                    try{
                        batchTaskService.deleteCodeFromSystemCode(itemCode);
                    }catch (Exception e){
                        log.info(LOG_ERROR_PREFIX+"件码****************************"+itemCode+"****************************删除异常!");
                        ScheduleErrorCode scheduleErrorCode = new ScheduleErrorCode();
                        scheduleErrorCode.setMachineCode(machineCode);
                        scheduleErrorCode.setQrCode(itemCode);
                        scheduleErrorCode.setRelationDate(produceDate);
                        scheduleErrorCode.setTableName(tableName);
                        scheduleErrorCode.setExecuteState(1);
                        batchTaskService.insertErrorCode(scheduleErrorCode);
                    }

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


    @Async("AsyncConfigure")
    public void asyncIllegalCodeTask(String tableName,int tableType){
        switch (tableType){
            case 2:
                log.info(LOG_INFO_PREFIX+"定时任务*****************多次关联****************************"+tableName);
                illegalCodeService.getRepeatCode(tableName,1);
                /**
                 * 包含废码的条
                 * */
                log.info(LOG_INFO_PREFIX+"定时任务*****************包含废码的条****************************"+tableName);
                illegalCodeService.containDiscardCodeStrip(tableName);
                /**
                 * 关联错误的条
                 * */
                log.info(LOG_INFO_PREFIX+"定时任务*****************关联错误的条****************************"+tableName);
                illegalCodeService.wrongRelationPackage(tableName);
                break;
            case 3:
                log.info(LOG_INFO_PREFIX+"定时任务*****************多次关联****************************"+tableName);
                illegalCodeService.getRepeatCode(tableName,2);
                /**
                 * 包含废码的件
                 * */
                log.info(LOG_INFO_PREFIX+"定时任务*****************包含废码的件****************************"+tableName);
                illegalCodeService.containDiscardCodeItem(tableName);
                /**
                 * 关联错误的件
                 * */
                log.info(LOG_INFO_PREFIX+"定时任务*****************关联错误的件****************************"+tableName);
                illegalCodeService.wrongRelationStrip(tableName);
                /**
                 * 包含剔除条码的件
                 * */
                log.info(LOG_INFO_PREFIX+"定时任务*****************包含剔除码的件****************************"+tableName);
                illegalCodeService.markedRejectItem(tableName);
        }
    }

}
