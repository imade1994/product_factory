package com.hlxd.microcloud.service.impl;


import com.hlxd.microcloud.dao.InitMapper;
import com.hlxd.microcloud.service.InitService;
import com.hlxd.microcloud.vo.InitMachineTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InitServiceImpl implements InitService {

    @Autowired
    InitMapper initMapper;

    @Override
    public List<InitMachineTimeVo> getInitMachineTime() {
        return initMapper.getInitMachineTime();
    }

    @Override
    public List<InitMachineTimeVo> getInitMachineTimeFromTable() {
        return initMapper.getInitMachineTimeFromTable();
    }

    @Override
    public void updateMachineTime(InitMachineTimeVo initMachineTimeVo) {
        initMapper.updateMachineTime(initMachineTimeVo);

    }
}
