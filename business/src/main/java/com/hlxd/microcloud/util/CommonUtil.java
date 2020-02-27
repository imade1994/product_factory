package com.hlxd.microcloud.util;

import com.hlxd.microcloud.vo.ProCode;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 类名: CommonUtil </br>
 * 描述: 通用工具类 </br>
 */
public final class CommonUtil {

    /**
     * influx 连接工厂
     * */
	public static final InfluxDB influxDb = InfluxDBFactory.connect("http://192.168.2.2:8086");


	public static final String dataBase  = "hlxd";


	public static final String time="yyyy-MM-dd";

    public static String UTCToCST(String UTCStr, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(UTCStr);
        System.out.println("UTC时间: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        //System.out.println("北京时间: " + calendar.getTime());
        SimpleDateFormat timeChange = new SimpleDateFormat(time);
        String returnTime = timeChange.format(calendar.getTime());
        return returnTime;
    }


    public static ProCode influxDbDateToJsonObject(String code){
        ProCode proCode = getProCodeFromResult(code,null,1);
        ProCode jianCode = new ProCode();
        if(null != proCode){
            switch (proCode.getType()){
                case "1":
                    ProCode tiaoCode = getProCodeFromResult(proCode.getParentCode(),proCode,1);
                    jianCode = getProCodeFromResult(tiaoCode.getParentCode(),tiaoCode,1);
                    break;
                case "2":
                    proCode = getProCodeFromResult(proCode.getQrCode(),proCode,2);
                    jianCode = getProCodeFromResult(proCode.getParentCode(),proCode,1);
                    break;
                case "3":
                    proCode = getProCodeFromResult(proCode.getQrCode(),proCode,2);
                    for(ProCode proCode1:proCode.getProCodes()){
                        getProCodeFromResult(proCode1.getQrCode(),proCode1,2);
                    }
                    jianCode= proCode;
            }
        }else{
            return null;
        }

        return jianCode;
    }

    /**
     * 生成对应实体对象
     * */
        public static Map getQueryData(List<String>columns,List<List<Object>> values,String str,int tag){
        List<ProCode> proCodes = new ArrayList<>();
        Map returnMap = new HashMap();
        Set<String> pCodeSet = new HashSet<>();
        Set<String> codeSet = new HashSet<>();
        for (List<Object> list : values) {
            ProCode proCode = new ProCode();
            BeanWrapperImpl bean = new BeanWrapperImpl(proCode);
            for(int i=0; i< list.size(); i++){
                String propertyName = columns.get(i);//字段名
                Object value = list.get(i);//相应字段值
                if(tag ==1){
                    if(null != str && propertyName.equals("parentCode") && !str.contains(String.valueOf(value))){
                        str += "select * from code where qrCode = '"+value+"';";
                    }
                    if(propertyName.equals("parentCode")){
                        pCodeSet.add(String.valueOf(value));
                    }
                    if(propertyName.equals("qrCode")){
                        codeSet.add(String.valueOf(value));
                    }
                }else if(tag ==2){
                    if(propertyName.equals("qrCode")){
                        codeSet.add(String.valueOf(value));
                    }
                    if(propertyName.equals("parentCode")){
                        pCodeSet.add(String.valueOf(value));
                    }
                }


                bean.setPropertyValue(propertyName, value);
            }

            proCodes.add(proCode);
        }
        if(tag ==1){
            returnMap.put("list",codeSet);
            returnMap.put("str",str);
        }else if(tag ==2){
            returnMap.put("list",codeSet);
            returnMap.put("pList",pCodeSet);
        }else{
            returnMap.put("list",proCodes);
        }
        return returnMap;

    }
    /***转义字段***/
    private static String setColumns(String column){
        String[] cols = column.split("_");
        StringBuffer sb = new StringBuffer();
        for(int i=0; i< cols.length; i++){
            String col = cols[i].toLowerCase();
            if(i != 0){
                String start = col.substring(0, 1).toUpperCase();
                String end = col.substring(1).toLowerCase();
                col = start + end;
            }
            sb.append(col);
        }
        return sb.toString();
    }
    /**
     * 包-条-件
     * */
    public static ProCode getProCodeFromResult(String code,ProCode proCode,int type){
        //查询获取码
        QueryResult results = new QueryResult();
        switch (type){
            case 1:
                Query query = new Query("select * from code where qrCode = '"+code+"'",dataBase);
                results = influxDb.query(query);
                break;
            case 2:
                Query query1 = new Query("select * from code where parentCode = '"+code+"'",dataBase);
                results = influxDb.query(query1);
                break;
            default:
                break;
        }
        //QueryResult results = influxDb.query(query);
        List<ProCode> proCodes = new ArrayList<>();
        List<ProCode> children = new ArrayList<>();
        if(null != results &&null != results.getResults()){
            for(QueryResult.Result result:results.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        List<List<Object>> values = serie.getValues();
                        List<String> columns = serie.getColumns();
                        Map map = getQueryData(columns, values,null,0);
                        proCodes.addAll((Collection<? extends ProCode>) map.get("list"));
                    }
                }else{
                    return null;
                }

            }
        }
        if(null != proCode){
            if(type == 1){
                ProCode fCode = proCodes.get(0);
                children.add(proCode);
                fCode.setProCodes(children);
                return fCode;
            }else{
                proCode.setProCodes(proCodes);
                return proCode;
            }
        }else{
            return proCodes.get(0);
        }
    }

    /**
     * 多sql查询
     *
     * */
    public static Map getCodeCount(String machineCode,String beginDate,String endDate){
        Map returnMap = new HashMap();
        Query query = new Query("select * from code where machineCode = '"+machineCode+"' and time >="+beginDate+" and time <="+endDate+"",dataBase);
        QueryResult results = influxDb.query(query);
        Set<String> proCodeList = new HashSet<>();
        String str = "";
        if(null != results &&null != results.getResults()){
            for(QueryResult.Result result:results.getResults()){
                List<QueryResult.Series> series = result.getSeries();
                if(null != series){
                    for(QueryResult.Series serie:series){
                        List<String> columns = serie.getColumns();
                        List<List<Object>> values = serie.getValues();
                        Map map = getQueryData(columns, values,str,1);
                        str = (String) map.get("str");
                        proCodeList.addAll((Collection<? extends String>) map.get("list"));
                    }
                }else{
                    returnMap.put(1,0);
                    returnMap.put(2,0);
                    returnMap.put(3,0);
                }
            }
        }//拼接完SQL查询件码数量
        if(proCodeList.size() > 0){
            Query jQuery = new Query(str,dataBase);
            QueryResult jResults = influ    xDb.query(jQuery);
            Set<String> proCodes = new HashSet<>();
            Set<String> jianCodes = new HashSet<>();
            int jCount = 0;
            if(null != jResults &&null != jResults.getResults()){
                for(QueryResult.Result result:jResults.getResults()){
                    List<QueryResult.Series> series = result.getSeries();
                    if(null != series){
                        for(QueryResult.Series serie:series){
                            List<List<Object>> values = serie.getValues();
                            List<String> columns = serie.getColumns();
                            Map map = getQueryData(columns, values,null,2);
                            //str = (String) map.get("str");
                            proCodes.addAll((Collection<? extends String>) map.get("list"));
                            jianCodes.addAll((Collection<? extends String>) map.get("pList"));
                        }
                    }else{
                        returnMap.put(1,0);
                        returnMap.put(2,0);
                        returnMap.put(3,0);
                    }

                }
            }
            returnMap.put(1,proCodeList);
            returnMap.put(2,proCodes);
            returnMap.put(3,jianCodes);
        }else{
            returnMap.put(1,0);
            returnMap.put(2,0);
            returnMap.put(3,0);
        }
        return returnMap;
    }
    /**
     * 时间机台
     *
     * */
    public static Map findCode(String machineCode,String beginDate,String endDate,String qrCode){
        Map returnMap = new HashMap();
        Query query = new Query("select * from code where machineCode = '"+machineCode+"' and time >="+beginDate+" and time <="+endDate+" and qrCode = '"+qrCode+"'",dataBase);
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
      }else{
        returnMap.put("status", 0);
        returnMap.put("msg", "码不存在！");

    }
        return returnMap;
    }



    /***
     * 根据时间段统计产量
     * 默认时间间隔5天
     * */

    public static Map getCodeCount(String beginDate,String endDate){
        //卷包，装箱 数据时间段统计sql
        String wrapStr = "select count(*) from code where time >="+beginDate+" and time <= "+endDate+" and type != '1' group by type;";
        //剔除时间段统计sql
        String packgeStr = "select count(*) from reject where time >="+beginDate+" and time <= "+endDate+" and type = '2' group by rejectType;select count(*) from reject where time >="+beginDate+" and time <= "+endDate+" and type = '3' group by rejectType";
        Query query = new Query(wrapStr+packgeStr,dataBase);
        Map returnMap = new HashMap();
        QueryResult queryResults = influxDb.query(query);
        if(null != queryResults &&null != queryResults.getResults()){
            List<QueryResult.Result> results= queryResults.getResults();
            for(int i=0;i<results.size();i++){
                List<QueryResult.Series> series = results.get(i).getSeries();
                Map map = new HashMap();
                if(null != series){
                    for(int k =1;k<series.size()+1;k++){
                        List<Object> values =  series.get(k-1).getValues().get(0);
                        map.put(series.get(k-1).getName().endsWith("code")?series.get(k-1).getTags().get("type"):series.get(k-1).getTags().get("rejectType"),values.get(1));
                    }
                }
                switch (i){
                    case 0:
                        returnMap.put("code",map);
                        break;
                    case 1:
                        returnMap.put("rejectCode_1",map);
                        break;
                    case 2:
                        returnMap.put("rejectCode_2",map);
                        break;
                    default:
                        break;
                }
                //List<String> values = (List<String>) results.get(i).getSeries().get(1);
            }
        }
        return returnMap;
    }

    /**
     * 统计默认查询
     * */
    public static Map getCountCodeCommen() throws ParseException {
        long endDate = new Date().getTime();
        long beginDate = endDate-6*24*60*60*1000;
        Map returnMap = new HashMap();
        String codeSql = "select count(*) from code where time >="+beginDate+"000000"+" and time <= "+endDate+"000000"+" and type = '2' group by time(1d)  fill(0); select count(*) from code where time >="+beginDate+"000000"+" and time <= "+endDate+"000000"+" and type = '3' group by time(1d)  fill(0) ;";
        String rejectSql = "select count(*) from reject where time >="+beginDate+"000000"+" and time <= "+endDate+"000000"+" and type = '2' group by time(1d)  fill(0); select count(*) from reject where time >="+beginDate+"000000"+" and time <= "+endDate+"000000"+" and type = '3' group by time(1d)  fill(0)";
        Query query = new Query(codeSql+rejectSql,dataBase);
        QueryResult queryResults = influxDb.query(query);
        List<QueryResult.Result> results = queryResults.getResults();
        int count=1;
        for(QueryResult.Result result:results){
            List<QueryResult.Series> querySeries = result.getSeries();
            Map map = new HashMap();
            if(null != querySeries){
                for(QueryResult.Series series:querySeries){
                    List<List<Object>> values = series.getValues();
                    for(int i=0;i<values.size();i++){
                        List<Object> list = values.get(i);
                        String date = UTCToCST(String.valueOf(list.get(0)),"yyyy-MM-dd'T'HH:mm:ss'Z'");
                        map.put(date,list.get(1));
                    }
                }
            }
            returnMap.put(count,map);
            count++;
        }
        return returnMap;

    }

  public static void main(String[] args) throws ParseException {
        String str = "2020-02-25T18:00:00Z";
        UTCToCST(str,"yyyy-MM-dd'T'HH:mm:ss'Z'");


    //
  }
}