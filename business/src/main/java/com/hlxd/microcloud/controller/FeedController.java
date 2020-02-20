package com.hlxd.microcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.FeedingRecord;
import com.hlxd.microcloud.entity.ProCode;
import com.hlxd.microcloud.entity.Throwing;
import com.hlxd.microcloud.service.ICodeService;
import com.hlxd.microcloud.service.IFeedingService;
import com.hlxd.microcloud.service.IThrowingService;
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
 * 喂丝控制器
 */
@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private IFeedingService iFeedingService;

    @Autowired
    private IThrowingService iThrowingService;

    @Autowired
    private ICodeService iCodeService;

    /**
     * 根据投丝机号和时间查码
     * @param feedTime
     * @param feedingMachineCode
     * @return
     *
     */
    @RequestMapping("/selectByFeed")
    public Map selectByFeed(String feedTime,String feedingMachineCode) throws ParseException {
        Map returnMap = new HashMap();
        if (feedTime!=null && feedTime!="" && feedingMachineCode!=null && feedingMachineCode!=""){

            FeedingRecord feedingRecord = new FeedingRecord();
            //根据投丝机号和时间获取制丝号
            feedingRecord = iFeedingService.selectByFeed(feedTime,feedingMachineCode);
            if(feedingRecord==null || "".equals(feedingRecord)){
                returnMap.put(CommonConstants.http_message,"码不存在");
            }else {
                Throwing throwing = new Throwing();
                //根据制丝号获取制丝时间
                throwing = iThrowingService.getThrowByOrder(feedingRecord.getThrowingNumber());
                if(throwing!=null){
                    String beginDate = throwing.getDoBeginDate();
                    String endDate = throwing.getDoEndDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String beginDate1 = String.valueOf(sdf.parse(beginDate).getTime()*1000000);
                    String endDate1 =String.valueOf(sdf.parse(endDate).getTime()*1000000);
                    Map requestMap = new HashMap();
                    //根据时间范围（喂丝的开始结束时间）取码
                    requestMap.put("q","SELECT qrCode FROM code WHERE time > "+beginDate1+" AND time < "+endDate1+" ");
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

                    //根据时间范围取码
                    //List<ProCode> proCodes = iCodeService.getCodeByTime(throwing.getDoBeginDate(),throwing.getDoEndDate());
                }else returnMap.put(CommonConstants.http_message,"码不存在");
            }

        }else returnMap.put(CommonConstants.http_message,"参数为空");

        return returnMap;
    }
}
