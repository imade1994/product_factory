package com.hlxd.microcloud.service;

import com.hlxd.microcloud.entity.MaterConsyme;

import java.util.List;

public interface MaterConsymeService {
    List<MaterConsyme> selectByMaterCode(String materialCode);
}
