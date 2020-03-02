package com.hlxd.microcloud.util;

import com.hlxd.microcloud.vo.ProCode;
import org.apache.ibatis.annotations.Mapper;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.access.method.P;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 类名: CommonUtil </br> 描述: 通用工具类 </br> */
public final class CommonUtil {

  /** influx 连接工厂 */
  public static final InfluxDB influxDb = InfluxDBFactory.connect("http://134.175.90.151:8086");

  public static final String dataBase = "hlxd";

  public static final String time = "yyyy-MM-dd";

  public static final String UTCString = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";

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
  public static Long UTCToLong(String UTCStr) throws ParseException {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat(UTCString);
    date = sdf.parse(UTCStr);
    return date.getTime();
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

 /* public static ProCode influxDbDateToJsonObject(String code) {
    *//*QueryResult queryResult = new QueryResult();
    Query query = new Query("select * from code where qrCode = '" + code + "' order by time desc", dataBase);
    queryResult = influxDb.query(query);
    Map returnMap = new HashMap();
    List<ProCode> proCodes = new ArrayList<>();
    if (null != queryResult
        && null != queryResult.getResults()
        && queryResult.getResults().size() > 0) { // 正常请求会有返回值，若此处为false说明可能网络异常
      for (QueryResult.Result result : queryResult.getResults()) {
        List<QueryResult.Series> series = result.getSeries();
        if (null != series) {//查询返回结果集，此处为空可能是没有相关数据
          for (QueryResult.Series serie : series) {
            List<String> columns = serie.getColumns();
            List<List<Object>> values = serie.getValues();
            Map map = getQueryData(columns, values, null, 0);
            proCodes.clear();
            proCodes.addAll((Collection<? extends ProCode>) map.get("list"));
          }
          //此处直接查询有返回结果集，继续以结果集的父码为条件查询件码  需判断是包码还是条码
          List<ProCode> proCodeList = new ArrayList<>();
          ProCode proCode = changeCode(proCodes.get(0));//返回实体应该是 件+list包
          proCodeList.add(proCodes.get(0));
          proCode.setProCodes(proCodeList);
          query =  new Query("select * from code where qrCode = '" + proCode.getQrCode() + "' order by time desc", dataBase);
          queryResult = influxDb.query(query);
          if (null != queryResult
              && null != queryResult.getResults()
              && queryResult.getResults().size() > 0) {
              // 正常请求会有返回值，若此处为false说明可能网络异常
              List<QueryResult.Series> series1 = result.getSeries();
              if(null != series1){
                  for (QueryResult.Series serie : series1) {
                      List<String> columns = serie.getColumns();
                      proCodes.clear();
                      List<List<Object>> values = serie.getValues();
                      Map map = getQueryData(columns, values, null, 0);
                      proCodes.addAll((Collection<? extends ProCode>) map.get("list"));
                  }
                  ProCode jianCode = changeCode(proCodes.get(0));
                  List<ProCode> proCodes1 = new ArrayList<>();
                  proCodes1.add(proCode);
                  jianCode.setProCodes(proCodes1);
                  returnMap.put("status", 1);
                  returnMap.put("data",jianCode);
              }else{
                  returnMap.put("status", 1);
                  returnMap.put("data",proCode);
              }
          }else{
              returnMap.put("status", 0);
              returnMap.put("data",proCode);
          }
        } else { // 出现此情况有两种  1：条包未装箱，所以查不到条包数据  2：码不存在
          // 先进行条包未装箱检查
          query = new Query("select * from code where parentCode = '" + code + "'order by time desc", dataBase);
          queryResult = influxDb.query(query);
          if (null != queryResult
              && null != queryResult.getResults()
              && queryResult.getResults().size() > 0) {
            for (QueryResult.Result result1 : queryResult.getResults()) {
              List<QueryResult.Series> series1 = result1.getSeries();
              if (null != series) {//此处查出来应该是条未装箱
                for (QueryResult.Series serie : series1) {
                  List<String> columns = serie.getColumns();
                  List<List<Object>> values = serie.getValues();
                  proCodes.clear();
                  Map map = getQueryData(columns, values, null, 0);
                  proCodes.addAll((Collection<? extends ProCode>) map.get("list"));
                }
                ProCode proCode = changeCode(proCodes.get(0));//取最新一条数据
                if(proCode.getType()!=3){
                    returnMap.put("status",1);
                    returnMap.put("data",proCode);//此处直接返回，因为以此码直接查询查询不到结果集，说明条未装箱
                }else{//此处else为了完善逻辑
                    //如果上面if为false的话，说明，件码未录入，只有 包-条 ，条-件 件码未存入数据库，暂时不做处理
                }
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
    } else {
      returnMap.put("status", 0);
      returnMap.put("msg","数据异常！");
    }*//*
    ProCode proCode = getProCodeFromResult(code, null, 1);
    ProCode jianCode = new ProCode();
    if (null != proCode) {
      switch (proCode.getType()) {
        case 2:
          jianCode = getProCodeFromResult(proCode.getQrCode(), proCode, 1);
          break;
        case 3:
          if(proCode.getProCodes().size()>0){
              ProCode tiaoCode = getProCodeFromResult(proCode.getProCodes().get(0).getQrCode(),null,2);

          }else{
             jianCode = getProCodeFromResult(proCode.getQrCode(),null,2);
             for(ProCode proCode1:jianCode.getProCodes()){
                 proCode1 = getProCodeFromResult(proCode1.getQrCode(),null,2);
             }
          }



          proCode = getProCodeFromResult(proCode.getProCodes().get(0).getQrCode(), proCode.getProCodes().get(0), 2);
          for (ProCode proCode1 : proCode.getProCodes()) {
            getProCodeFromResult(proCode1.getQrCode(), proCode1, 2);
          }
          jianCode = proCode;
      }
    } else {
      proCode = getProCodeFromResult(code, null, 2);
    }
    return jianCode;
  }*/

  /** 生成对应实体对象 */
  public static List<ProCode> getQueryData(
      List<String> columns, List<List<Object>> values) {
    List<ProCode> proCodes = new ArrayList<>();
    for (List<Object> list : values) {
      ProCode proCode = new ProCode();
      BeanWrapperImpl bean = new BeanWrapperImpl(proCode);
      for (int i = 0; i < list.size(); i++) {
        String propertyName = columns.get(i); // 字段名
        Object value = list.get(i); // 相应字段值
        bean.setPropertyValue(propertyName, value);
      }
      proCodes.add(proCode);
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
   *
   * */
  public static void insertCodeRelationFromMap(Map map) throws ParseException {
    List<ProCode> jianCode = (List<ProCode>) map.get("3");
    List<ProCode> tiaoCode = (List<ProCode>) map.get("2");
    List<ProCode> baoCode = (List<ProCode>) map.get("1");
    BatchPoints batchPoints = BatchPoints.database(dataBase)
            .consistency(InfluxDB.ConsistencyLevel.ALL)
            .build();
    for(int i=0;i<jianCode.size();i++){
      ProCode jian = jianCode.get(i);
      for(int j=0;j<tiaoCode.size();j++){
        ProCode tiao = tiaoCode.get(j);
        if(jian.getQrCode().equals(tiao.getParentCode())){
          for(int k=0;k<baoCode.size();k++){
            ProCode bao = baoCode.get(k);
            if(bao.getParentCode().equals(tiao.getQrCode())){
              Point point=Point.measurement("codeRelation")
                      .tag("qrCode",bao.getQrCode())
                      .tag("baoCode",tiao.getQrCode())
                      .tag("jianCode",jian.getQrCode())
                      .tag("brandName",bao.getProductId())
                      .tag("jMachineCode",jian.getMachineCode())
                      .tag("bMachineCode",bao.getMachineCode())
                      .field("jRemark",jian.getRemark())
                      .field("bRemark",bao.getRemark())
                      //.tag("jVerifyStatus",jian.getVerifyStatus())
                      //.tag("bVerifyStatus",bao.getVerifyStatus())
                      .tag("jProduceDate",jian.getTime())
                      .tag("bProduceDate",bao.getTime())
                      .time(UTCToLong(bao.getTime()), TimeUnit.MILLISECONDS)
                      .build();
              batchPoints.point(point);
            }
          }
        }
      }
    }
    if(batchPoints.getPoints().size()>0){
      //int j=1;
      influxDb.write(batchPoints);
    }
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

  public static void main(String[] args){

    String prifix = "HTTPS://TB2D.CN/01/";
    String endfix = "/21/01234567895";
    List<String> jian = new ArrayList<>();
    List<String> tiao = new ArrayList<>();
    List<String> bao = new ArrayList<>();
    //influxDb.enableBatch(10000,10000,TimeUnit.SECONDS);
    influxDb.enableGzip();
    Pong pong =influxDb.ping();
    if(pong==null){
      System.out.println("数据库连接异常");
    }else{
      System.out.println("连接成功，ping值为"+pong.getResponseTime());
    }
    BatchPoints batchPoints = BatchPoints.database(dataBase)
            .consistency(InfluxDB.ConsistencyLevel.ALL)
            .build();
    do{
      String uuid = prifix+0+UUID.randomUUID().toString().replaceAll("-","")+endfix;
      if(!bao.contains(uuid)){
        //System.out.println(uuid);
        bao.add(uuid);
      }
    }while(bao.size()<150000);//包码
    do{
      String uuid = prifix+1+UUID.randomUUID().toString().replaceAll("-","")+endfix;
      if(!tiao.contains(uuid)){
       /// System.out.println(uuid);
        tiao.add(uuid);
      }
    }while(tiao.size()<15000);//条码
    do{
      String uuid = prifix+2+UUID.randomUUID().toString().replaceAll("-","")+endfix;
      if(!jian.contains(uuid)){
        //System.out.println(uuid);
        jian.add(uuid);
      }
    }while(jian.size()<300);//件码
    Map map = new HashMap();
    for(int i = 0;i<30;i++){//30天
      long day = new Date().getTime();
      day = day-i*24*60*60*1000;
      Map newMap = new HashMap();
      List<String> baoList = bao.subList(i*5000,(i+1)*5000);
      List<String> tiaoList = tiao.subList(i*500,(i+1)*500);
      List<String> jianList = jian.subList(i*10,(i+1)*10);
      newMap.put(1,baoList);
      newMap.put(2,tiaoList);
      newMap.put(3,jianList);
      map.put(day,newMap);
    }
    Long jTime=null;
    Long bTime = null;
    for(Object object:map.keySet()){
      jTime = Long.valueOf(String.valueOf(object));
      bTime = Long.valueOf(String.valueOf(object));
      Map tem = (Map) map.get(object);
      List<String> jianList = (List<String>) tem.get(3);
      List<String> tiaoList = (List<String>) tem.get(2);
      List<String> baoList = (List<String>) tem.get(1);
      for(int i=0;i<jianList.size();i++){
        List<String> temTiaoList = tiaoList.subList(i*50,(i+1)*50);
        for(int k=0;k<temTiaoList.size();k++){
          List<String> temBaoList = baoList.subList((50*i+k)*10,(50*i+k+1)*10);
          for(String s:temBaoList){
            Point point= Point.measurement("code")
                    .tag("qrCode",s)
                    .tag("type","1")
                    .tag("parentCode",temTiaoList.get(k))
                    .tag("machineCode","G18")
                    .tag("productId","云烟(软珍)")
                    .tag("verifyStatus","0")
                    .field("remark","测试第"+500*i+k+"条烟")
                    .time(bTime*1000000,TimeUnit.NANOSECONDS)
                    .build();
            batchPoints.point(point);
          }
          Point point= Point.measurement("code")
                  .tag("qrCode",temTiaoList.get(k))
                  .tag("type","2")
                  .tag("parentCode",jianList.get(i))
                  .tag("machineCode","G1")
                  .tag("productId","云烟(软珍)")
                  .tag("verifyStatus","0")
                  .field("remark","测试第"+500*i+k+"条烟")
                  .time(bTime*1000000,TimeUnit.NANOSECONDS)
                  .build();
          batchPoints.point(point);
          bTime = bTime +1;
        }
        Point point= Point.measurement("code")
                .tag("qrCode",jianList.get(i))
                .tag("type","3")
                .tag("machineCode","G1")
                .tag("productId","云烟(软珍)")
                .tag("verifyStatus","0")
                .field("remark","测试第"+i+"件烟")
                .time(jTime*1000000,TimeUnit.NANOSECONDS)
                .build();
        jTime = jTime + 500;
        batchPoints.point(point);
      }
    }
    influxDb.write(batchPoints);
  }
}
