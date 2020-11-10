package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.BatchTaskService;
import com.hlxd.microcloud.util.ThreadManager;
import com.hlxd.microcloud.vo.CodeUnion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/10/2610:03
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@RestController
@RequestMapping("/task")
@Slf4j
public class BatchTaskController {

    private final String url = "jdbc:mysql://localhost:3306/qrcode?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
    private final String user = "root";
    private final String pwd = "Hlxd@123456";





    @Autowired
    private BatchTaskService batchTaskService;

    public  Connection getConn(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pwd);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }




    @RequestMapping("/initCode")
    public void  initCode(@RequestParam("end")int end,@RequestParam("begin")int begin){
        //ExecutorService exec = Executors.newFixedThreadPool(50);
        // 创建任务集合
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        Map param = new HashMap();
        param.put("end",end);
        param.put("begin",begin);
        List<CodeUnion> codeUnionList = batchTaskService.getCodeUnion(param);
        int forCount = codeUnionList.size()%1000 == 0 ?codeUnionList.size()/1000:(codeUnionList.size()/1000)+1;
        Long t1 = System.currentTimeMillis();
        log.info("*******************************************返回总数为"+codeUnionList.size());
        try{
            for(int i=0;i<forCount;i++){
                List<CodeUnion> subList = new ArrayList<>();
                if (((i+1)*1000 ) > codeUnionList.size()){
                     subList = codeUnionList.subList(i*1000,codeUnionList.size());
                }else{
                     subList = codeUnionList.subList(i*1000,(i+1)*1000);
                }

                int finalI = i;
                /*List<CodeUnion> finalSubList = subList;*/
                List<CodeUnion> finalSubList = subList;
                Callable<Integer> queryCall = new Callable<Integer>() {
                    @Override
                    public Integer call() throws SQLException {
                        /*Connection connection = null;*/
                        try{
                            /*connection = getConn();
                            connection.setAutoCommit(false);
                            String sql = "insert into t_hl_system_code_union (qr_code,package_code,item_code,machine_code,machine_name,package_machine_code,package_machine_name,relation_date,package_relation_date,brand_id,shift_id,package_shift_id) values (?,?,?, ?,?,?, ?,?,?, ?,?,?) ";
                            PreparedStatement stmt = connection.prepareStatement(sql);
                            for(CodeUnion codeUnion: finalSubList){
                                stmt.setString(1,codeUnion.getQrCode());
                                stmt.setString(2,codeUnion.getPackageCode());
                                stmt.setString(3,codeUnion.getItemCode());
                                stmt.setString(4,codeUnion.getMachineCode());
                                stmt.setString(5,codeUnion.getMachineName());
                                stmt.setString(6,codeUnion.getPackageMachineCode());
                                stmt.setString(7,codeUnion.getPackageMachineName());
                                stmt.setString(8,codeUnion.getRelationDate());
                                stmt.setString(9,codeUnion.getPackageRelationDate());
                                stmt.setString(10,codeUnion.getBrandId());
                                stmt.setString(11,codeUnion.getShiftId());
                                stmt.setString(11,codeUnion.getPackageShiftId());
                                stmt.addBatch();
                            }
                            log.info(stmt.toString());
                            stmt.executeBatch();
                            connection.commit();
                            list.add(finalI);*/
                            batchTaskService.BatchInsertCodeUnion(finalSubList);
                            return finalI;
                        }catch (Exception e){
                            e.printStackTrace();
                            log.info("线程出现异常"+e.getMessage());
                            return -1;
                        }
//                        }finally{
//                            connection.close();
//                        }
                        //return true;
                    }
                };
                FutureTask<Integer> CodeUnion = new FutureTask<>(queryCall);
                taskList.add(CodeUnion);
                //exec.submit(CodeUnion);
                ThreadManager.getLongPool().execute(CodeUnion);
            }
        }catch (Exception e){
            log.error("循环异常"+e.getMessage());
            e.printStackTrace();

        }
        for(FutureTask task :taskList){
            try{
                int result = (int) task.get();
                if(result != -1){
                    log.info("task执行成功**************************");
                }else if(result == -1){
                    log.info("task执行失败**************************");
                }
            } catch (InterruptedException e) {
                log.error("异常"+e.getMessage());
                e.printStackTrace();
            } catch (ExecutionException e) {
                log.error("异常"+e.getMessage());
                e.printStackTrace();
            }
        }
        Long t2 = System.currentTimeMillis();
        log.info("执行完成,用时…………"+(t2-t1));
    }
}
