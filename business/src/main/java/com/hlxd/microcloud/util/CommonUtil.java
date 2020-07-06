package com.hlxd.microcloud.util;

import com.alibaba.druid.util.StringUtils;
import com.hlxd.microcloud.vo.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.beans.BeanWrapperImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** 类名: CommonUtil </br> 描述: 通用工具类 </br> */
@Slf4j
public final class CommonUtil {

    static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .readTimeout(100,TimeUnit.SECONDS)
            .writeTimeout(100,TimeUnit.SECONDS);

  /** influx 连接工厂 */
  public static final InfluxDB influxDb = InfluxDBFactory.connect("http://127.0.0.1:8086",client);

  public static final String dataBase = "hlxd";

  public static final String time = "yyyy-MM-dd";

  public static final String timeFormate = "yyyy-MM-dd HH:mm:ss.S";

  public static final String UTCString = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";

  public static final String UTCString2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String UTCString1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  public static final Map queryMap = new HashMap();


  public static final Map queryTimeMap = new HashMap();



  public static final String key1 = "beginDate";

  public static final String key2 = "endDate";


  public static final String key4 = "period";

  public static final String key5 = "machineCode";

  public static final String sql1 = " and time >= ";

  public static final String sql2 = " and time <= ";


  public static final String sql4 = " and period = ";

  public static final String sql5 = " and machineCode = ";

  private static AtomicInteger atomicInteger = new AtomicInteger(0);



  static{
      queryMap.put(key4,sql4);
      queryMap.put(key5,sql5);

  }
  static {
      queryTimeMap.put(key1,sql1);
      queryTimeMap.put(key2,sql2);
  }



  public static String UTCToCST(String UTCStr, String format) throws ParseException {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    date = sdf.parse(UTCStr);
    System.out.println("UTC时间: " + date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
    // calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
    // System.out.println("北京时间: " + calendar.getTime());
    SimpleDateFormat timeChange = new SimpleDateFormat(time);
    String returnTime = timeChange.format(calendar.getTime());
    return returnTime;
  }
  public static String UTCToStr(String UTCStr) throws ParseException {
      Date date = null;
      SimpleDateFormat sdfUTC = new SimpleDateFormat(UTCString);
      SimpleDateFormat sdfSTR = new SimpleDateFormat(UTCString);
      date = sdfUTC.parse(UTCStr);
      String str = sdfSTR.format(date);

      return str;
  }
  /**
   * 时间对比工具类
   * @Param  t1 开始时间 t2 结束时间
   *
   * */
  public static Long compareTime(String t1,String t2) throws ParseException {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormate);
      Date d1 = simpleDateFormat.parse(t1);
      Date d2 = simpleDateFormat.parse(t2);
      return d1.getTime()-d2.getTime();

  }

  public static String getFormateString(){
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time);
    return simpleDateFormat.format(date);
  }

  public static Date UTCToStr1(String UTCStr) throws ParseException {
      Date date = null;
      SimpleDateFormat sdfUTC = new SimpleDateFormat(UTCString1);
      SimpleDateFormat sdfSTR = new SimpleDateFormat(time);
      date = sdfUTC.parse(UTCStr);
      String time = sdfSTR.format(date);
      date = sdfSTR.parse(time);
      return date;
  }

  public static String UTCToStr2(String UTCStr) throws ParseException {
      Date date = null;
      if(UTCStr.length() == 30){
          UTCStr = UTCStr.substring(0,UTCStr.length()-7)+"Z";
      }
      SimpleDateFormat sdfUTC = new SimpleDateFormat(UTCString2);
      SimpleDateFormat sdfSTR = new SimpleDateFormat(time);
      date = sdfUTC.parse(UTCStr);
      String time = sdfSTR.format(date);
      return time;
  }

  public static String UTCToStr3(String UTCStr) throws ParseException {
      Date date = null;

      SimpleDateFormat sdfUTC = new SimpleDateFormat(UTCString2);
      SimpleDateFormat sdfSTR = new SimpleDateFormat(time);
      date = sdfUTC.parse(UTCStr);
      String time = sdfSTR.format(date);
      return time;
  }

  public static int getPeriod(String UTCStr) throws ParseException {
      Date date = null;
      SimpleDateFormat sdfUTC = new SimpleDateFormat(UTCString1);
      //SimpleDateFormat sdfSTR = new SimpleDateFormat(UTCString);
      date = sdfUTC.parse(UTCStr);
      int type = 0;
      switch (date.getHours()){
          case 0:
              type= 1;
              break;
          case 8:
              type= 2;
              break;
          case 16:
              type= 3;
              break;
          default:
              break;
      }
      return type;
  }


  public static Long UTCToLong(String UTCStr) throws ParseException {
    Date date = null;
    String Str = "";
    switch (UTCStr.length()){
        case 20:
            Str = UTCString1;
            break;
        case 23:
            Str = UTCString;
            break;
        case 24:
            Str = UTCString2;
            break;
        default:
            Str = UTCString;
            break;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(Str);
    date = sdf.parse(UTCStr);
    return date.getTime();
  }
  public static String UTCToString(String UTCStr) throws ParseException {
        Date date = null;
        String returnStr = "";
        String Str = "";
        switch (UTCStr.length()){
            case 20:
                Str = UTCString1;
                break;
            case 23:
                Str = UTCString;
                break;
            case 24:
                Str = UTCString2;
                break;
            default:
                Str = UTCString;
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Str);
        date = sdf.parse(UTCStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time);
        returnStr =simpleDateFormat.format(date);
        return returnStr;
    }

  public static ProCode changeCode(ProCode oldProCode) {
    ProCode proCode = new ProCode();
    proCode.setTime(oldProCode.getTime());
    List<ProCode> proCodes = new ArrayList<>();
    proCodes.add(oldProCode);
    proCode.setProCodes(proCodes);
    proCode.setMachineCode(oldProCode.getMachineCode());
    proCode.setProductId(oldProCode.getProductId());
    proCode.setQrCode(oldProCode.getParentCode());
    proCode.setRemark(oldProCode.getRemark());
    proCode.setType(oldProCode.getType()+1);
    return proCode;
  }

  public static Map influxDbDateToJsonObject(String code) throws InstantiationException, IllegalAccessException {
    QueryResult queryResult = new QueryResult();
    Query query = new Query("select * from codeRelation where qrCode = '" + code + "' or baoCode = '"+code+"' or jianCode = '"+code+"' order by time desc", dataBase);

    queryResult = influxDb.query(query);
    Map returnMap = new HashMap();
    List proCodes = new ArrayList<>();
    List<ProCode> codeRelationList = new ArrayList<>();
    if (null != queryResult
        && null != queryResult.getResults()
        && queryResult.getResults().size() > 0) { // 正常请求会有返回值，若此处为false说明可能网络异常
      for (QueryResult.Result result : queryResult.getResults()) {
        List<QueryResult.Series> series = result.getSeries();
        if (null != series) {//查询返回结果集，此处为空可能是没有相关数据
          for (QueryResult.Series serie : series) {
            proCodes=getQueryData(serie, CodeRelation.class);
            returnMap.put("status",1);
            returnMap.put("data",proCodes);
          }
        } else { // 出现此情况有两种  1：条包未装箱，所以查不到条包数据  2：码不存在
          // 先进行条包未装箱检查
          query = new Query("select * from code where qrCode = '" + code + "'order by time desc", dataBase);
          queryResult = influxDb.query(query);
          if (null != queryResult
              && null != queryResult.getResults()
              && queryResult.getResults().size() > 0) {
            for (QueryResult.Result result1 : queryResult.getResults()) {
              List<QueryResult.Series> series1 = result1.getSeries();
              if (null != series1) {//此处查出来应该是条未装箱
                for (QueryResult.Series serie : series1) {
                    codeRelationList=getQueryData(serie, ProCode.class);
                  //proCodes.addAll(null);
                }
                //ProCode proCode = changeCode(proCodes.get(0));//取最新一条数据
                  List<CodeRelation> codeRelations = new ArrayList<>();
                  for(ProCode proCode:codeRelationList){
                      CodeRelation codeRelation = new CodeRelation();
                      codeRelation.setTime(proCode.getTime());
                      codeRelation.setBaoCode(proCode.getParentCode());
                      codeRelation.setMachineCode(proCode.getMachineCode());
                      codeRelation.setBProduceDate(proCode.getTime());
                      codeRelation.setBrandName(proCode.getProductId());
                      codeRelation.setBRemark(proCode.getRemark());
                      codeRelation.setBVerifyStatus(proCode.getVerifyStatus());
                      codeRelation.setQrCode(proCode.getQrCode());
                      codeRelations.add(codeRelation);
                  }
               // ProCode proCode = (ProCode) proCodes.get(0);

                returnMap.put("status",1);
                returnMap.put("data",codeRelations);
              } else { // 查询返回结果集为空
                returnMap.put("status", 0);
                returnMap.put("msg","码不存在！");
              }
            }
          } else { // 数据查询异常
            returnMap.put("status", 0);
            returnMap.put("msg","数据异常！");
          }
        }
      }
    }
    return returnMap;
  }

  /** 生成对应实体对象 */
  public static List getQueryData(
          QueryResult.Series series, java.lang.Class t) throws IllegalAccessException, InstantiationException {
      List<String> columns = series.getColumns();
      List<List<Object>> values = series.getValues();
      Map<String,String> map = series.getTags();
      if(null != map){
          for(String s:map.keySet()){
              columns.add(s);
              for(int i=0;i<values.size();i++){
                  List<Object> temList = values.get(i);
                  temList.add(map.get(s));
              }
          }
      }
      List proCodes = new ArrayList<>();
    for (List<Object> list : values) {
        Object object = t.newInstance();
      BeanWrapperImpl bean = new BeanWrapperImpl(object);
      for (int i = 0; i < list.size(); i++) {
        String propertyName = columns.get(i); // 字段名
        Object value = list.get(i); // 相应字段值
        bean.setPropertyValue(propertyName, value);
      }
        proCodes.add(object);
    }
    return proCodes;
  }
  /** *转义字段** */
  private static String setColumns(String column) {
    String[] cols = column.split("_");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < cols.length; i++) {
      String col = cols[i].toLowerCase();
      if (i != 0) {
        String start = col.substring(0, 1).toUpperCase();
        String end = col.substring(1).toLowerCase();
        col = start + end;
      }
      sb.append(col);
    }
    return sb.toString();
  }


  /**
   * map转换数组
   *插入時序數據庫
   *
   * */
  public static void insertCodeRelationFromMap(Map map) throws ParseException {
    Map<String,List<ProCode>> baoMap = (Map<String, List<ProCode>>) map.get("1");
    log.info("包MAP大小-*----------------------"+baoMap.keySet().size());
    Map<String,List<ProCode>> tiaoMap = (Map<String, List<ProCode>>) map.get("2");
    log.info("件MAP大小-*----------------------"+tiaoMap.keySet().size());
    if(!influxDb.isBatchEnabled()){
        influxDb.enableBatch(10000,10000,TimeUnit.MILLISECONDS);
    }
    //AtomicInteger i= new AtomicInteger();
    if(!influxDb.isGzipEnabled()){
        influxDb.enableGzip();
    }
    long beginDate = new Date().getTime();
    for(String s:tiaoMap.keySet()){
        if(!s.equals("")){
            List<ProCode> temPro = tiaoMap.get(s);
            for(ProCode proCode:temPro){
                List<ProCode> temList = baoMap.get(proCode.getQrCode());
                if(null != temList){
                    for(ProCode proCode1:temList){
                        try {
                            Point point = Point.measurement("codeRelation")
                                    .tag("qrCode",proCode1.getQrCode())
                                    .tag("baoCode",proCode1.getParentCode())
                                    .tag("jianCode",proCode.getParentCode())
                                    .tag("brandName",proCode1.getProductId())
                                    .tag("jMachineCode",proCode.getMachineCode())
                                    .tag("machineCode",proCode1.getMachineCode())
                                    .field("jRemark",proCode.getRemark())
                                    .field("bRemark",proCode1.getRemark())
                                    .field("jQrCode",proCode.getParentCode())
                                    .tag("jVerifyStatus",proCode.getVerifyStatus())
                                    .tag("bVerifyStatus",proCode1.getVerifyStatus())
                                    .tag("jProduceDate",proCode.getTime())
                                    .tag("bProduceDate",proCode1.getTime())
                                    .time(UTCToLong(proCode1.getTime()), TimeUnit.MILLISECONDS)
                                    .build();
                            influxDb.write(dataBase,"autogen",point);
                        } catch (ParseException e) {
                            log.info("异常+++++++++++++++++++++包码信息"+proCode1.toString());
                            log.info("异常+++++++++++++++++++++条码信息"+proCode.toString());
                            //e.printStackTrace();
                            continue;
                        }

                    }
                }else{
                    log.info("此条码不存在包码*******************"+proCode.getQrCode());
                }

            }
            log.info("任务处理完成+******************************************************************");
        }
    }
    System.out.println("循环总耗时***********************************"+(new Date().getTime()-beginDate));
  }

    /**
     * map转换数组
     *插入時序數據庫
     *
     * */
    public static void insertCodeCountByPeriod(List<CodeCount> codeCounts) throws ParseException {
        if(!influxDb.isBatchEnabled()){
            influxDb.enableBatch(10000,10000,TimeUnit.MILLISECONDS);
        }
        //AtomicInteger i= new AtomicInteger();
        if(!influxDb.isGzipEnabled()){
            influxDb.enableGzip();
        }

        for(CodeCount codeCount:codeCounts){
            Point point=Point.measurement("codeCount")
                    .tag("count_remark",codeCount.getCount_remark())
                    .tag("type",codeCount.getType())
                    .tag("machineCode",codeCount.getMachineCode())
                    .tag("period",String.valueOf(codeCount.getPeriod()))
                    .field("remark","測試統計")
                    .time(codeCount.getDate(), TimeUnit.MILLISECONDS)
                    .build();
            influxDb.write(dataBase,"autogen",point);
        }
        //influxDb.close();
    }




    /**
     * 时间段+机台号
     * 产量查询
     * */
    public static Map getCountFromPeriod(Map<String,String[]> map) throws InstantiationException, IllegalAccessException, ParseException {
        String sql ="select * from scanCount where 1=1 ";//统计
        String sql1 = " select * from rejectCount where 1=1 ";
        Map<String,Map> countMap = new HashMap();
        Map<String,Map> rejectMap = new HashMap();
        Map<String,Map> returnMap = new HashMap();
        String temSql = "";
        String temSql1 = "";
        for(String s:map.keySet()){
            if(null != map.get(s)){
                String[] temValue = map.get(s);
                if(queryMap.containsKey(s)){
                    temSql = temSql+queryMap.get(s)+"'"+temValue[0]+"' ";
                }else if(queryTimeMap.containsKey(s)){
                    temSql = temSql+queryTimeMap.get(s)+temValue[0]+" ";
                    temSql1 = temSql1+queryTimeMap.get(s)+temValue[0]+" ";
                }

            }
        }
        sql = sql+temSql+" group by type order by time desc ";
        sql1 = sql1+temSql1 + " group by type,rejectType order by time desc";
        Query query = new Query(sql+";"+sql1, dataBase);
        QueryResult queryResult = influxDb.query(query);
        List<CodeCount> codeCountList = new ArrayList<>();
        if (null != queryResult
                && null != queryResult.getResults()
                && queryResult.getResults().size() > 0) { // 正常请求会有返回值，若此处为false说明可能网络异常
            for (QueryResult.Result result : queryResult.getResults()) {
                List<QueryResult.Series> series = result.getSeries();
                if (null != series) {//查询返回结果集，此处为空可能是没有相关数据
                    for (QueryResult.Series serie : series) {
                        String name = serie.getName();
                        Map<String,String> tags= serie.getTags();
                        codeCountList=getQueryData(serie, CodeCount.class);
                        Map<String,List<CodeCount>> temMap = new HashMap();
                        String type = tags.get("type");
                        if(name.equals("scanCount")){
                            for(CodeCount codeCount:codeCountList){
                                String time = UTCToStr2(codeCount.getTime());
                                if(temMap.containsKey(time)){
                                    temMap.get(time).add(codeCount);
                                }else{
                                    List<CodeCount> codeCounts = new ArrayList<>();
                                    codeCounts.add(codeCount);
                                    temMap.put(time,codeCounts);
                                }
                            }
                            if(countMap.containsKey(type)){
                                countMap.get(type).putAll(temMap);
                            }else{
                                countMap.put(type,temMap);
                            }
                            returnMap.put("0",countMap);
                        }else{
                            String rejectType = tags.get("rejectType");
                            Map<String,List<CodeCount>> tem = null == rejectMap.get(type)?new HashMap<>(): (Map<String, List<CodeCount>>) rejectMap.get(type);
                            for(CodeCount codeCount:codeCountList){
                                //String time = UTCToStr2(codeCount.getTime());
                                if(tem.containsKey(rejectType)){
                                    tem.get(rejectType).add(codeCount);
                                }else{
                                    List<CodeCount> codeCounts = new ArrayList<>();
                                    codeCounts.add(codeCount);
                                    tem.put(rejectType,codeCounts);
                                }
                            }
                            if(rejectMap.containsKey(type)){
                                rejectMap.get(type).putAll(tem);
                            }else{
                                rejectMap.put(type,tem);
                            }
                            returnMap.put("1",rejectMap);
                        }
                    }
                }
            }
        }
        //influxDb.close();
        return  returnMap;
    }
    public static Map getCodeCountFromPeriod(Map<String,String[]> map) throws InstantiationException, IllegalAccessException {
        String sql1 ="select * from scanCount where 1=1 ";
       // String sql2 ="select count(*) from code where type = '1'  ";
        Map returnMap = new HashMap();
        String temSql = "";
        for(String s:map.keySet()){
            if(null != map.get(s)){
                String[] temValue = map.get(s);
                if(queryTimeMap.containsKey(s)){
                    temSql = temSql+queryTimeMap.get(s)+temValue[0]+" ";
                }else if(queryMap.containsKey(s)&&!s.equals("machineCode") ){
                    temSql = temSql+queryMap.get(s)+"'"+temValue[0]+"' ";
                }
            }
        }
        sql1 = sql1+temSql+" group by type";
        //sql2 = sql2+temSql;
        Query query = new Query(sql1, dataBase);
        QueryResult queryResult = influxDb.query(query);
        if(null != queryResult&&null !=queryResult.getResults() && queryResult.getResults().size()>0){
            for (QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        List<CountVo> countVos = new ArrayList<>();
                        countVos=getQueryData(serie, CountVo.class);
                        int count = 0;
                        int type = (Integer.valueOf(serie.getTags().get("type"))==1)?1:3;
                        for(CountVo countVo:countVos){
                            count = count+countVo.getApproval();
                        }
                        CountVo countVo = new CountVo();
                        switch (type){
                            case 1:
                                countVo.setCount_remark(count);
                                break;
                            case 3:
                                countVo.setCount(count/50);
                                break;
                            default:
                                break;
                        }
                        List<CountVo> countVoList = new ArrayList<>();
                        countVoList.add(countVo);
                        returnMap.put(type,countVoList);
                        /*if(countVo.size()>0 && null !=countVo.get(0).getCount_remark()){
                            returnMap.put("1",countVo);
                        }else if(countVo.size()>0 && null !=countVo.get(0).getCount()){
                            returnMap.put("3",countVo);
                        }*/
                    }
                }
            }
        }
        //influxDb.close();
        return returnMap;
    }


    /**
     * 精准查找
     * */
    public static Map findCode(Map<String,String[]> map){
        String sql = "select * from codeRelation where 1=1 ";
        String sql1 = "select * from code where 1=1 ";
        String temSql = "";
        Map returnMap = new HashMap();
        for(String s:map.keySet()){
            if(null != map.get(s)){
                String[] temValue = map.get(s);
                if(queryTimeMap.containsKey(s)){
                    temSql = temSql+queryTimeMap.get(s)+temValue[0]+" ";
                }else if(queryMap.containsKey(s) ){
                    temSql = temSql+queryMap.get(s)+"'"+temValue[0]+"' ";
                }else if(s.equals("qrCode")){
                    temSql = temSql+" and qrCode = '"+temValue[0]+"' or baoCode = '"+temValue[0]+"' or jianCode= '"+temValue[0]+"'";
                }
            }
        }
        sql = sql+temSql;
        sql1 = sql1+temSql;
        Query query = new Query(sql+";"+sql1, dataBase);
        QueryResult queryResult = influxDb.query(query);
        boolean flag = false;
        if(null != queryResult&&null !=queryResult.getResults() && queryResult.getResults().size()>0){
            for (QueryResult.Result result:queryResult.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        if(serie.getValues().size()>0){
                            flag = true;
                        }
                    }
                }
            }
        }
        if(flag){
            returnMap.put("status",1);
            returnMap.put("msg","码存在!");
        }else{
            returnMap.put("status",0);
            returnMap.put("msg","码不在所选条件范围内!");
        }
        return returnMap;
    }





 /* *//** 包-条-件 *//*
  public static ProCode getProCodeFromResult(String code, ProCode proCode, int type) {
    // 查询获取码
    ProCode returnCode = new ProCode();
    QueryResult results = new QueryResult();
    switch (type) {
      case 1:
        Query query = new Query("select * from code where qrCode = '" + code + "' order by time desc", dataBase);
        results = influxDb.query(query);
        break;
      case 2:
        Query query1 = new Query("select * from code where parentCode = '" + code + "' order by time desc", dataBase);
        results = influxDb.query(query1);
        break;
      default:
        break;
    }
    List<ProCode> proCodes = new ArrayList<>();
    if (null != results && null != results.getResults()) {
      for (QueryResult.Result result : results.getResults()) {
        List<QueryResult.Series> series = result.getSeries();
        if (null != series) {
          for (QueryResult.Series serie : series) {
            List<List<Object>> values = serie.getValues();
            List<String> columns = serie.getColumns();
            Map map = getQueryData(columns, values, null, 0);
            proCodes.addAll((Collection<? extends ProCode>) map.get("list"));
          }
          ProCode newCode = proCodes.get(0);
          switch (type){
              case 1:
                  if(newCode.getType()==3){
                      returnCode= newCode;
                  }else{
                      returnCode= changeCode(newCode);
                  }
                  break;
              case 2:

          }

        } else {
          return null;
        }
      }
    }else{
        return null;
    }
      return returnCode;

  }*/

  /** 多sql查询 *//*
  public static Map getCodeCount(String machineCode, String beginDate, String endDate) {
    Map returnMap = new HashMap();
    Query query =
        new Query(
            "select * from code where machineCode = '"
                + machineCode
                + "' and time >="
                + beginDate
                + " and time <="
                + endDate
                + "",
            dataBase);
    QueryResult results = influxDb.query(query);
    Set<String> proCodeList = new HashSet<>();
    String str = "";
    if (null != results && null != results.getResults()) {
      for (QueryResult.Result result : results.getResults()) {
        List<QueryResult.Series> series = result.getSeries();
        if (null != series) {
          for (QueryResult.Series serie : series) {
            List<String> columns = serie.getColumns();
            List<List<Object>> values = serie.getValues();
            Map map = getQueryData(columns, values, str, 1);
            str = (String) map.get("str");
            proCodeList.addAll((Collection<? extends String>) map.get("list"));
          }
        } else {
          returnMap.put(1, 0);
          returnMap.put(2, 0);
          returnMap.put(3, 0);
        }
      }
    } // 拼接完SQL查询件码数量
    if (proCodeList.size() > 0) {
      Query jQuery = new Query(str, dataBase);
      QueryResult jResults = influxDb.query(jQuery);
      Set<String> proCodes = new HashSet<>();
      Set<String> jianCodes = new HashSet<>();
      int jCount = 0;
      if (null != jResults && null != jResults.getResults()) {
        for (QueryResult.Result result : jResults.getResults()) {
          List<QueryResult.Series> series = result.getSeries();
          if (null != series) {
            for (QueryResult.Series serie : series) {
              List<List<Object>> values = serie.getValues();
              List<String> columns = serie.getColumns();
              Map map = getQueryData(columns, values, null, 2);
              // str = (String) map.get("str");
              proCodes.addAll((Collection<? extends String>) map.get("list"));
              jianCodes.addAll((Collection<? extends String>) map.get("pList"));
            }
          } else {
            returnMap.put(1, 0);
            returnMap.put(2, 0);
            returnMap.put(3, 0);
          }
        }
      }
      returnMap.put(1, proCodeList);
      returnMap.put(2, proCodes);
      returnMap.put(3, jianCodes);
    } else {
      returnMap.put(1, 0);
      returnMap.put(2, 0);
      returnMap.put(3, 0);
    }
    return returnMap;
  }
  *//** 时间机台 *//*
  public static Map findCode(String machineCode, String beginDate, String endDate, String qrCode) {
    Map returnMap = new HashMap();
    Query query =
        new Query(
            "select * from code where machineCode = '"
                + machineCode
                + "' and time >="
                + beginDate
                + " and time <="
                + endDate
                + " and qrCode = '"
                + qrCode
                + "'",
            dataBase);
    QueryResult results = influxDb.query(query);
    if (null != results && results.getResults().size() > 0) {
      for (QueryResult.Result result : results.getResults()) {
        if (null != result.getSeries()) {
          returnMap.put("status", 1);
          returnMap.put("msg", "码存在！");
        } else {
          returnMap.put("status", 0);
          returnMap.put("msg", "码不存在！");
        }
      }
    } else {
      returnMap.put("status", 0);
      returnMap.put("msg", "码不存在！");
    }
    return returnMap;
  }

  *//** * 根据时间段统计产量 默认时间间隔5天 *//*
  public static Map getCodeCount(String beginDate, String endDate) {
    // 卷包，装箱 数据时间段统计sql
    String wrapStr =
        "select count(*) from code where time >="
            + beginDate
            + " and time <= "
            + endDate
            + " and type != '1' group by type;";
    // 剔除时间段统计sql
    String packgeStr =
        "select count(*) from reject where time >="
            + beginDate
            + " and time <= "
            + endDate
            + " and type = '2' group by rejectType;select count(*) from reject where time >="
            + beginDate
            + " and time <= "
            + endDate
            + " and type = '3' group by rejectType";
    Query query = new Query(wrapStr + packgeStr, dataBase);
    Map returnMap = new HashMap();
    QueryResult queryResults = influxDb.query(query);
    if (null != queryResults && null != queryResults.getResults()) {
      List<QueryResult.Result> results = queryResults.getResults();
      for (int i = 0; i < results.size(); i++) {
        List<QueryResult.Series> series = results.get(i).getSeries();
        Map map = new HashMap();
        if (null != series) {
          for (int k = 1; k < series.size() + 1; k++) {
            List<Object> values = series.get(k - 1).getValues().get(0);
            map.put(
                series.get(k - 1).getName().endsWith("code")
                    ? series.get(k - 1).getTags().get("type")
                    : series.get(k - 1).getTags().get("rejectType"),
                values.get(1));
          }
        }
        switch (i) {
          case 0:
            returnMap.put("code", map);
            break;
          case 1:
            returnMap.put("rejectCode_1", map);
            break;
          case 2:
            returnMap.put("rejectCode_2", map);
            break;
          default:
            break;
        }
        // List<String> values = (List<String>) results.get(i).getSeries().get(1);
      }
    }
    return returnMap;
  }

  *//** 统计默认查询 *//*
  public static Map getCountCodeCommen() throws ParseException {
    long endDate = new Date().getTime();
    long beginDate = endDate - 6 * 24 * 60 * 60 * 1000;
    Map returnMap = new HashMap();
    String codeSql =
        "select count(*) from code where time >="
            + beginDate
            + "000000"
            + " and time <= "
            + endDate
            + "000000"
            + " and type = '2' group by time(1d)  fill(0); select count(*) from code where time >="
            + beginDate
            + "000000"
            + " and time <= "
            + endDate
            + "000000"
            + " and type = '3' group by time(1d)  fill(0) ;";
    String rejectSql =
        "select count(*) from reject where time >="
            + beginDate
            + "000000"
            + " and time <= "
            + endDate
            + "000000"
            + " and type = '2' group by time(1d)  fill(0); select count(*) from reject where time >="
            + beginDate
            + "000000"
            + " and time <= "
            + endDate
            + "000000"
            + " and type = '3' group by time(1d)  fill(0)";
    Query query = new Query(codeSql + rejectSql, dataBase);
    QueryResult queryResults = influxDb.query(query);
    List<QueryResult.Result> results = queryResults.getResults();
    int count = 1;
    for (QueryResult.Result result : results) {
      List<QueryResult.Series> querySeries = result.getSeries();
      Map map = new HashMap();
      if (null != querySeries) {
        for (QueryResult.Series series : querySeries) {
          List<List<Object>> values = series.getValues();
          for (int i = 0; i < values.size(); i++) {
            List<Object> list = values.get(i);
            String date = UTCToCST(String.valueOf(list.get(0)), "yyyy-MM-dd'T'HH:mm:ss'Z'");
            map.put(date, list.get(1));
          }
        }
      }
      returnMap.put(count, map);
      count++;
    }
    return returnMap;
  }*/

    public static Map transformMap(Map<String,String[]> map){
        Map returnMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:map.keySet()){
            String[] str = map.get(s);
            for(String t:str){
                stringBuilder.append(t);
            }
            returnMap.put(s,stringBuilder.toString());
            stringBuilder.delete(0,stringBuilder.length());
        }
        return returnMap;
    }



    public static int validateParams(String s){
        String[] validate = CommomStatic.CHECKSQL.split("\\|");
        for(String str:validate){
            if(s.indexOf(str)>0){
                return -1;
            }
        }
        return 0;
    }


    public static Husband newHusband(){
        List<String> specialities = new ArrayList<>();
        Husband husband = new Husband();
        husband.setFeature("瓜子脸");
        husband.setHeight(180);
        husband.setIncome(100000);
        husband.setSkinColour("白");
        specialities.add("唱");
        specialities.add("跳");
        specialities.add("rap");
        specialities.add("篮球");
        husband.setSpecialities(specialities);
        husband.setProfessional("练习时长两年半的练习生");
        husband.setWeight(150);
        return husband;
    }

  public static void main(String[] args){


      String str = "t_hl_code_batch_202005140001";

      System.out.println(validateParams(str));

  }

  public static String getCode(String prifix,String endfix){
      return prifix+1+UUID.randomUUID().toString().replaceAll("-","")+endfix;
  }
}
