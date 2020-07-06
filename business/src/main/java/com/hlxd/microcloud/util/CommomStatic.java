package com.hlxd.microcloud.util;

import java.util.HashMap;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/5/1315:34
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public final class CommomStatic {

    public static final String SUCCESS="0";

    public static final String FAIL="1";

    public static final String MESSAGE="message";

    public static final String DATA="data";

    public static final String STATUS="status";


    public static final String BEGINDATE="beginDate";

    public static final String ENDDATE="endDate";

    public static final String TYPE="type";

    public static final String TIME="time";


    public static final String SUCCESS_MESSAGE="success";

    public static Map wrongTags = new HashMap();


    /**
     * 错误对照码
     * */
    public static final String illegal_User = "40001";//非法用户

    public static final String illegal_Params = "40002";//非法参数

    public static final String lack_Params = "40007";//缺少必要参数

    public static final String illegal_operation = "40003";//非法操作

    public static final String illegal_token = "40004";//非法凭证

    public static final String illegal_request = "40005";//非法请求

    public static final String expire_token = "40006";//过期凭证

    static{
        wrongTags.put(illegal_User,"非法用户！");
        wrongTags.put(illegal_Params,"非法参数！");
        wrongTags.put(lack_Params,"缺少必要参数！");
        wrongTags.put(illegal_operation,"非法操作！");
        wrongTags.put(illegal_token,"非法凭证！");
        wrongTags.put(illegal_request,"非法请求！");
        wrongTags.put(expire_token,"过期凭证！");
    }


    /**
     * 自动生成表名前缀
     * */
    public static final String TABLE_NAME_PRIFIX = "t_hl_code_batch_";


  /** 防sql 注入正则匹配 */
  public static final String CHECKSQL = "|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
}
