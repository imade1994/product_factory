package com.hlxd.microcloud.service;

import com.hlxd.microcloud.vo.Qrcode;
import org.springframework.stereotype.Service;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/1310:48
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT qrCodeExcute
 */
@Service
public interface QrcodeService {

    Qrcode getQrCode(String qrCode);
}
