package com.hlxd.microcloud.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.ProCode;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 类名: CommonUtil </br>
 * 描述: 通用工具类 </br>
 */
public final class CommonUtil {
    //appId wx93f9c01cfdcb58ee
    public static final String appId="wx82fb36369de2f8ec";
    //开发者密码
    public static final String appSecret="5ae3e9ae3626474eaa2b471e4651fcb2";
	


    // 凭证Token（GET）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 凭证Ticket（GET）
    public final static String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    
    /**
     	*     发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
        	System.out.println("连接超时：{}");
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("https请求异常：{}");
        }
        return jsonObject;
    }

    
    
    
    /**
     * URL编码（utf-8）
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 	创建nonce_str
     * @return
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 	创建timestamp
     * @return
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    public static Date UTCToCST(String UTCStr, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(UTCStr);
        System.out.println("UTC时间: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        //System.out.println("北京时间: " + calendar.getTime());
        return calendar.getTime();
    }


    public static  ProCode influxDbDateToJsonObject(String code){
        Map requestMap = new HashMap();
        requestMap.put("q","select * from code where qrCode = '"+code+"'");
        requestMap.put("db","hlxd");
        String returnText = HttpClientUtil.post("http://10.130.0.216:8086/query",requestMap);
        JSONArray jsonArray = textToSingleJsonArray(returnText,1);
        ProCode proCode=JsonObjectToProCode(jsonArray);
        if(null != proCode && proCode.getType().equals("0")){//二维码为包码，溯源上层
            ProCode tiaoCode = getAllProCode(proCode);
            //以上为条-包关系
            ProCode jianCode = getAllProCode(tiaoCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("1")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            ProCode jianCode = getAllProCode(proCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("2")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            for(ProCode proCode1:proCode.getProCodes()){
                getAllProCodeFromTiaoToBao(proCode1);
            }
            //
            // ProCode jianCode = getAllProCode(proCode);
            return proCode;
        }
        return proCode;
    }
    public static  ProCode influxDbDateToJsonObjectFromCondition(String machineCode,String beginDate,String endDate){
        Map requestMap = new HashMap();
        requestMap.put("q","select * from code where machineCode = '"+machineCode+"' and time >'"+beginDate+"' and time <'"+endDate+"'");
        requestMap.put("db","hlxd");
        String returnText = HttpClientUtil.post("http://10.130.0.216:8086/query",requestMap);
        JSONArray jsonArray = textToSingleJsonArray(returnText,1);
        ProCode proCode=JsonObjectToProCode(jsonArray);
        if(null != proCode && proCode.getType().equals("0")){//二维码为包码，溯源上层
            ProCode tiaoCode = getAllProCode(proCode);
            //以上为条-包关系
            ProCode jianCode = getAllProCode(tiaoCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("1")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            ProCode jianCode = getAllProCode(proCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("2")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            for(ProCode proCode1:proCode.getProCodes()){
                getAllProCodeFromTiaoToBao(proCode1);
            }
            //
            // ProCode jianCode = getAllProCode(proCode);
            return proCode;
        }
        return proCode;
    }

    public static  ProCode influxDbDateToJsonObject(String code){
        Map requestMap = new HashMap();
        requestMap.put("q","select * from code where qrCode = '"+code+"'");
        requestMap.put("db","hlxd");
        String returnText = HttpClientUtil.post("http://10.130.0.216:8086/query",requestMap);
        JSONArray jsonArray = textToSingleJsonArray(returnText,1);
        ProCode proCode=JsonObjectToProCode(jsonArray);
        if(null != proCode && proCode.getType().equals("0")){//二维码为包码，溯源上层
            ProCode tiaoCode = getAllProCode(proCode);
            //以上为条-包关系
            ProCode jianCode = getAllProCode(tiaoCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("1")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            ProCode jianCode = getAllProCode(proCode);
            return jianCode;
        }else if(null != proCode && proCode.getType().equals("2")){
            getAllProCodeFromTiaoToBao(proCode);//查询下属包数量,赋值给传入的条Code
            for(ProCode proCode1:proCode.getProCodes()){
                getAllProCodeFromTiaoToBao(proCode1);
            }
            //
            // ProCode jianCode = getAllProCode(proCode);
            return proCode;
        }
        return proCode;
    }


    public static JSONArray textToSingleJsonArray(String text,int type){
        JSONArray returnArray = new JSONArray();
        JSONObject jsonObject7 = JSONObject.parseObject(text);
        JSONArray jsonArray8 = (JSONArray) jsonObject7.get("results");
        JSONObject jsonObject9 = (JSONObject) jsonArray8.get(0);
        if(null != jsonObject9.get("series")){
            JSONArray jsonArray10 = (JSONArray) jsonObject9.get("series");
            JSONObject jsonObject11 = (JSONObject) jsonArray10.get(0);
            switch (type){
                case 1:
                    JSONArray jsonArray2 = (JSONArray) jsonObject11.get("values");
                    returnArray = (JSONArray) jsonArray2.get(0);//单条数据
                    break;
                case 2:
                    returnArray= (JSONArray) jsonObject11.get("values");//多条数据
                    break;
                default:
                    break;
            }
        }
        return returnArray;
    }



    public static ProCode JsonObjectToProCode(JSONArray jsonArray){
        ProCode proCode = new ProCode();
        if(null!= jsonArray && jsonArray.size()>0){
            proCode.setProduceDate((String) jsonArray.get(0));
            proCode.setMachineName((String) jsonArray.get(1));
            proCode.setQrCode((String) jsonArray.get(2));
            proCode.setProductName((String) jsonArray.get(3));
            proCode.setParentCode((String) jsonArray.get(4));
            proCode.setRemark((String) jsonArray.get(5));
            proCode.setType((String) jsonArray.get(6));
            proCode.setVerifyStatus((String) jsonArray.get(7));
        }
        return proCode;
    }

    public static List<ProCode> JsonObjectToListProCode(JSONArray jsonArray){
        List<ProCode> proCodes = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            List<String> strings = (List<String>) jsonArray.get(i);
            ProCode proCode = new ProCode();
            proCode.setProductName(strings.get(3));
            proCode.setParentCode(strings.get(4));
            proCode.setRemark(strings.get(5));
            proCode.setProduceDate(strings.get(0));
            proCode.setMachineName(strings.get(1));
            proCode.setQrCode(strings.get(2));
            proCode.setType(strings.get(6));
            proCode.setVerifyStatus(strings.get(7));
            proCodes.add(proCode);
        }
        return proCodes;
    }


    public static ProCode getAllProCode(ProCode proCode){
        Map requestMap = new HashMap();
        requestMap.put("q","select * from code where qrCode = '"+proCode.getParentCode()+"'");//查询件数据
        requestMap.put("db","hlxd");
        String returnText = HttpClientUtil.post("http://10.130.0.216:8086/query",requestMap);
        JSONArray jianArray = textToSingleJsonArray(returnText,1);//json 序列化返回数组
        ProCode code = JsonObjectToProCode(jianArray);
        List<ProCode> codes = new ArrayList<>();
        codes.add(proCode);
        code.setProCodes(codes);
        return code;
    }

    public static ProCode getAllProCodeFromTiaoToBao(ProCode proCode){
        Map requestMap = new HashMap();
        requestMap.put("q","select * from code where parent_code = '"+proCode.getQrCode()+"'");//查询件数据
        requestMap.put("db","hlxd");
        String returnText = HttpClientUtil.post("http://10.130.0.216:8086/query",requestMap);
        JSONArray jianArray = textToSingleJsonArray(returnText,2);//json 序列化返回数组
        List<ProCode> proCodes = JsonObjectToListProCode(jianArray);
        //List<ProCode> codes = new ArrayList<>();
        proCode.setProCodes(proCodes);
        return proCode;
    }


}