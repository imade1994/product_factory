package com.hlxd.microcloud.dao;


import com.hlxd.microcloud.vo.CodeCheckVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeCheckMapper {

    CodeCheckVo getCodeCheckVo(@Param("qrcode") String qrcode);


    void deleteCodeCheck(List<String> codeList);

}
