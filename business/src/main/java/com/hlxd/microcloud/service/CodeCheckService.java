package com.hlxd.microcloud.service;


import com.hlxd.microcloud.vo.CodeCheckVo;

import java.util.List;

public interface CodeCheckService {



    CodeCheckVo getCodeCheckVo(String qrcode);


    void deleteCodeCheck(List<String> codeList);
}
