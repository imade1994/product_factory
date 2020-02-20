package com.hlxd.microcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.MaterConsyme;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IThrowingService;
import com.hlxd.microcloud.service.MaterConsymeService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import com.hlxd.microcloud.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.Configuration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mater")
public class MaterConsumeControlelr {

    @Autowired
    private MaterConsymeService materConsymeService;

    @Autowired
    private WrapService wrapService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 根据物料查码
     * @param materialCode
     * @return
     */
    @RequestMapping("/selectByMaterVode")
    public Map selectByMaterVode(String materialCode) throws ParseException {
        Map returnMap = new HashMap();
        Set set = new HashSet();
        if(materialCode!=null || materialCode!=""){
            //根据物料代码获取对象信息
            List<MaterConsyme> materConsymes = materConsymeService.selectByMaterCode(materialCode);
            //迭代物料对象信息取出制丝或卷包批次号
            for (MaterConsyme mater:materConsymes){
                //根据工单号或者制丝号获取对象信息
                WrapOrder wrapOrder = wrapService.selectByNumber(mater.getBatchNumber());
                Throwing throwing = iThrowingService.getThrowByOrder(mater.getBatchNumber());
                if (wrapOrder==null && throwing==null){
                    returnMap.put(CommonConstants.http_message,"码不存在");
                }else {
                    if (wrapOrder!=null){
                        String beginDate = wrapOrder.getDoBeginDate();
                        String endDate = wrapOrder.getDoEndDate();
                        String machineCode = wrapOrder.getMachineCode();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String beginDate1 = String.valueOf(sdf.parse(beginDate).getTime()*1000000);
                        String endDate1 =String.valueOf(sdf.parse(endDate).getTime()*1000000);
                        Map requestMap = new HashMap();
                        //根据机台号和时间查码
                        requestMap.put("q","SELECT qrCode FROM code" +
                                " WHERE machineCode='"+machineCode+"'" +
                                "and time > "+beginDate1+" AND time < "+endDate1+" ");
                        requestMap.put("db","hlxd");
                        String returnText = HttpClientUtil.post("http://localhost:8086/query",requestMap);
                        JSONObject json = JSON.parseObject(returnText);
                        List list1 = (List) json.get("results");
                        Map map = (Map) list1.get(0);
                        List seriesList =(List)map.get("series");
                        if(seriesList==null){
                            requestMap.put(CommonConstants.http_message,"码不存在");
                        }else {
                            Map series0 = (Map)seriesList.get(0);
                            List<List> valuesList = (List<List>) series0.get("values");
                            List list2 = new ArrayList();
                            if (valuesList.isEmpty()){
                                requestMap.put(CommonConstants.http_message,"码不存在");
                            }else {
                                for(List list3: valuesList){
                                    list2.add(list3.get(1));
                                }
                                set.addAll(list2);
                            }
                        }
                        //根据卷包机台，时间获取码
                        //List<ProCode> proCodes = iCodeService.getCodeByWrap(wrapOrder.getMachineCode(),wrapOrder.getDoBeginDate(),wrapOrder.getDoEndDate());
                        //if (proCodes.size()>0){
                          //  list.addAll(proCodes);
                        //}
                    }
                    if (throwing!=null){
                        String beginDate = throwing.getDoBeginDate();
                        String endDate = throwing.getDoEndDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String beginDate1 = String.valueOf(sdf.parse(beginDate).getTime()*1000000);
                        String endDate1 =String.valueOf(sdf.parse(endDate).getTime()*1000000);
                        Map requestMap = new HashMap();
                        //根据时间范围取码
                        requestMap.put("q","SELECT qrCode FROM code WHERE time > "+beginDate1+" AND time < "+endDate1+" ");
                        requestMap.put("db","hlxd");
                        String returnText = HttpClientUtil.post("http://localhost:8086/query",requestMap);
                        JSONObject json = JSON.parseObject(returnText);
                        List list1 = (List) json.get("results");
                        Map map = (Map) list1.get(0);
                        List seriesList =(List)map.get("series");
                        if (seriesList==null){
                            requestMap.put(CommonConstants.http_message,"码不存在");
                        }else {
                            Map series0 = (Map)seriesList.get(0);
                            List<List> valuesList = (List<List>) series0.get("values");
                            List list2 = new ArrayList();
                            if (valuesList.isEmpty()){
                                requestMap.put(CommonConstants.http_message,"码不存在");
                            }else {
                                for(List list3: valuesList){
                                    list2.add(list3.get(1));
                                }
                                set.addAll(list2);
                            }
                        }
                        //List<ProCode> proCodes1 = iCodeService.getCodeByTime(throwing.getDoBeginDate(),throwing.getDoEndDate());
                        //if (proCodes1.size()>0){
                        //    list.addAll(proCodes1);
                        //}
                    }
                }
            }
        }else returnMap.put(CommonConstants.http_message,"参数为空");

        returnMap.put(CommonConstants.http_data,set);

        return returnMap;
    }

}
