package com.hlxd.microcloud.controller;


import com.hlxd.microcloud.service.CodeCheckService;
import com.hlxd.microcloud.util.CommomStatic;
import com.hlxd.microcloud.vo.CodeCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/check")
public class CodeCheckController {


    @Autowired
    CodeCheckService codeCheckService;






     @RequestMapping("/checkCode")
    public Map checkCodeRelation(@RequestParam("code")String code){
         Map returnMap = new HashMap();
         CodeCheckVo codeCheckVo = codeCheckService.getCodeCheckVo(code);
         if(null != codeCheckVo){
             returnMap.put(CommomStatic.STATUS,CommomStatic.SUCCESS);
             returnMap.put(CommomStatic.DATA,codeCheckVo);
         }else{
             returnMap.put(CommomStatic.STATUS,CommomStatic.FAIL);
             returnMap.put(CommomStatic.MESSAGE,"该编码未关联！");
         }
         return returnMap;
     }

     @RequestMapping("/deleteCode")
    public void deleteCheckedCode(List<String> codeList){
         codeCheckService.deleteCodeCheck(codeList);
     }


}
