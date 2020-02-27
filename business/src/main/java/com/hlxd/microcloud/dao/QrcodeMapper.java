package com.hlxd.microcloud.dao;

import com.hlxd.microcloud.vo.Qrcode;
import org.apache.ibatis.annotations.Mapper;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/2/1310:47
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT qrCodeExcute
 */
@Mapper
public interface QrcodeMapper {

    Qrcode getQrcode(String qrCode);
}
