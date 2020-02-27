package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.QrcodeMapper;
import com.hlxd.microcloud.service.QrcodeService;
import com.hlxd.microcloud.vo.Qrcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/1310:49
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT qrCodeExcute
 */
@Service
public class QrcodeServiceImpl implements QrcodeService {
    @Autowired
    private QrcodeMapper qrcodeMapper;

    @Override
    public Qrcode getQrCode(String qrCode) {
        return qrcodeMapper.getQrcode(qrCode);
    }
}
