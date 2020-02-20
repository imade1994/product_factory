package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.MaterConsymeMapper;
import com.hlxd.microcloud.entity.MaterConsyme;
import com.hlxd.microcloud.service.MaterConsymeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterConsymeServiceImpl implements MaterConsymeService {

    @Autowired(required = false)
    private MaterConsymeMapper materConsymeMapper;

    @Override
    public List<MaterConsyme> selectByMaterCode(String materialCode) {
        return materConsymeMapper.selectByMaterCode(materialCode);
    }
}
