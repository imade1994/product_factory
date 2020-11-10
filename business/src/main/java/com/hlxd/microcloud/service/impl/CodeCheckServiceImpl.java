package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.CodeCheckMapper;
import com.hlxd.microcloud.vo.CodeCheckVo;
import com.hlxd.microcloud.service.CodeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CodeCheckServiceImpl implements CodeCheckService {

    @Autowired
    CodeCheckMapper codeCheckMapper;
    @Override
    public CodeCheckVo getCodeCheckVo(String qrcode) {
        return codeCheckMapper.getCodeCheckVo(qrcode);
    }

    @Override
    public void deleteCodeCheck(List<String> codeList) {
        codeCheckMapper.deleteCodeCheck(codeList);

    }
}
