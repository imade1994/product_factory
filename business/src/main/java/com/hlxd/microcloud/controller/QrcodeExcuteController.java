package com.hlxd.microcloud.controller;

import com.hlxd.microcloud.service.QrcodeService;
import com.hlxd.microcloud.vo.Qrcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/1311:59
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT qrCodeExcute
 */
@RestController
@RequestMapping("/api/code")
public class QrcodeExcuteController {
    @Autowired
    private QrcodeService qrcodeService;


    @RequestMapping("/getDetails")
    public Map getCodeDetails(@RequestParam("qrCode")String qrCode){
        Map returnMap = new HashMap();
        Qrcode qrcode = qrcodeService.getQrCode(qrCode);
        if(null != qrCode){
            returnMap.put("status",1);
            returnMap.put("data",qrcode);
        }else{
            returnMap.put("status",0);
            returnMap.put("msg","码不存在或者不为条或者件码！");
        }
        return returnMap;
    }
}
