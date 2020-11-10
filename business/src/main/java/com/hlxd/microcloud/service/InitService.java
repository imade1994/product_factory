package com.hlxd.microcloud.service;


import com.hlxd.microcloud.vo.InitMachineTimeVo;

import java.util.List;

public interface InitService {

    List<InitMachineTimeVo> getInitMachineTime();

    List<InitMachineTimeVo> getInitMachineTimeFromTable();




    void updateMachineTime(InitMachineTimeVo initMachineTimeVo);



}
