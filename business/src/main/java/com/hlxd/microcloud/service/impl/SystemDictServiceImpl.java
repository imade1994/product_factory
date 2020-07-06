package com.hlxd.microcloud.service.impl;

import com.hlxd.microcloud.dao.SystemDictMapper;
import com.hlxd.microcloud.service.SystemDictService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.SystemDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.sun.tools.doclint.Entity.and;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/2514:55
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
@Service
public class SystemDictServiceImpl implements SystemDictService {
    @Autowired
    SystemDictMapper systemDictMapper;
    @Override
    public List<SystemDict> getDictList(Map map) {
        return systemDictMapper.getDictList(map);
    }

    @Override
    public Map insertSystemDict(SystemDict systemDict) {
        Map param = new HashMap();
        Map returnMap = new HashMap();
        if(null != systemDict){
            if(null != systemDict.getParentId()){
                param.put("id",systemDict.getParentId());
                if(null != systemDict.getParentId()&& systemDict.getParentId().equals("0")){
                    systemDict.setId(UUID.randomUUID().toString());
                    systemDictMapper.insertSystemDict(systemDict);
                    returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                    returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
                }else if(null != systemDict.getParentId()&& !systemDict.getParentId().equals("0")){
                    List<SystemDict> systemDictList = systemDictMapper.getDictList(param);
                    if(systemDictList.size()<=0){
                        returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                        returnMap.put(CommomStatic.MESSAGE,"父类不存在！");
                    }else{
                        systemDict.setId(UUID.randomUUID().toString());
                        systemDictMapper.insertSystemDict(systemDict);
                        returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                        returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
                    }
                }

            }
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"数据不存在！");
        }
        return returnMap;
    }

    @Override
    public void updateSystemDict(SystemDict systemDict) {
        systemDictMapper.updateSystemDict(systemDict);
    }

    @Override
    public Map deleteSystemDict(Map map) {
        Map returnMap = new HashMap();
        List<SystemDict> systemDicts = systemDictMapper.getDictList(map);
        if(systemDicts.size()>0){
            map.clear();
            map.put("parentId",systemDicts.get(0).getId());
            List<SystemDict> systemDictList = systemDictMapper.getDictList(map);
            map.put("id",systemDicts.get(0).getId());
            if(systemDictList.size()>0){
                returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
                returnMap.put(CommomStatic.MESSAGE,"该数据字典存在下级！无法删除");
            }else{
                systemDictMapper.deleteSystemDict(map);
                returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
                returnMap.put(CommomStatic.MESSAGE,CommomStatic.SUCCESS_MESSAGE);
            }
        }else{
            returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
            returnMap.put(CommomStatic.MESSAGE,"该数据不存在！");
        }
        return returnMap;
    }

    @Override
    public List<SystemDict> getAllDictList(Map map) {
        return systemDictMapper.getAllDictList(map);
    }

}
