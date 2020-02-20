package com.hlxd.microcloud.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.WrapOrder;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.WrapService;
import com.hlxd.microcloud.util.CommonConstants;
import com.hlxd.microcloud.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卷包工单控制器
 */
@RestController
@RequestMapping("/wrap")
public class WarpOrderController {

    @Autowired
    private WrapService wrapService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 按卷包工单查码
     * @param machineCode
     * @param produceDate
     * @param classId
     * @return
     */
    @RequestMapping("/selectByWrapOrder")
    public Map selectByWrapOrder(String machineCode,String produceDate,String classId) throws ParseException {
        Map returnMap = new HashMap();
        //List<ProCode> proCodes =new ArrayList<>();
        if(machineCode!=""&& machineCode!=null && produceDate!="" && produceDate!=null && classId!=""&&classId!=null){
            //工单查询
            List<WrapOrder> wrapOrders = wrapService.selectByWrapOrder(machineCode,produceDate,classId);
            if(wrapOrders.isEmpty() || wrapOrders.size()==0){
                returnMap.put(CommonConstants.http_message,"码不存在！");
            }else {
                for (WrapOrder wrapOrder:wrapOrders){
                    //根据工单信息查码
                    String beginDate = wrapOrder.getDoBeginDate();
                    String endDate = wrapOrder.getDoEndDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String beginDate1 = String.valueOf(sdf.parse(beginDate).getTime()*1000000);
                    String endDate1 =String.valueOf(sdf.parse(endDate).getTime()*1000000);
                    Map requestMap = new HashMap();
                    //根据机台号与工单生产开始结束时间查码
                    requestMap.put("q","SELECT qrCode FROM code" +
                            " WHERE machineCode='"+machineCode+"'" +
                            "and time > "+beginDate1+" AND time < "+endDate1+" ");
                    requestMap.put("db","hlxd");
                    String returnText = HttpClientUtil.post("http://localhost:8086/query",requestMap);
                    JSONObject json = JSON.parseObject(returnText);
                    List list = (List) json.get("results");
                    Map map = (Map) list.get(0);
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
                            for(List list1: valuesList){
                                list2.add(list1.get(1));
                            }
                        }
                        returnMap.put(CommonConstants.http_data,list2);
                    }
                    //System.out.println(list2);
                    //List<ProCode> proCodes1= iCodeService.getCodeByWrap(machineCode,wrapOrder.getDoBeginDate(),wrapOrder.getDoEndDate());
                    //proCodes.addAll(proCodes1);
                }
            }
        }else {
            returnMap.put(CommonConstants.http_message,"参数为空");
        }
        return  returnMap;
    }

}
