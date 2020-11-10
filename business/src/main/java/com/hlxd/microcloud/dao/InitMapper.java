package com.hlxd.microcloud.dao;



import com.hlxd.microcloud.vo.InitMachineTimeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InitMapper {


    List<InitMachineTimeVo> getInitMachineTime();

    List<InitMachineTimeVo> getInitMachineTimeFromTable();




    void updateMachineTime(InitMachineTimeVo initMachineTimeVo);








}
