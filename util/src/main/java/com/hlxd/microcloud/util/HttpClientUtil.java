package com.hlxd.microcloud.util;

import com.alibaba.druid.util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/1/211:17
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
public class HttpClientUtil {
    public static final String ENCODING = "UTF-8";

    public static String get(final String url) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
//        //返回的数据包不进行压缩，解决content length为-1的问题
//        httpGet.setHeader("Accept-Encoding", "identity");
        System.out.println("executing get request " + httpGet.getURI());
        try {
            //执行get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                //获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    result = EntityUtils.toString(entity, ENCODING);
//                    System.out.println("Response content: " + result);
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }

        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String post(final String url, Map<String, Object> params) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            //获取请求的参数
            HttpEntity requestEntity = getRequestEntity(params);
            httpPost.setEntity(requestEntity);

            System.out.println("executing post request " + httpPost.getURI());
            //post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                //获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    result = EntityUtils.toString(entity, ENCODING);
                    System.out.println("Response content: " + result);
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 通过参数获取HttpPost请求需要的参数
     * 如果参数有文件，则在key之前添加file_前缀，如要传的文件key为cover,则map的key为file_cover
     * @param params
     * @return
     */
    public static HttpEntity getRequestEntity(Map<String, Object> params) {
        Map<String, AbstractContentBody> paramsPart = Maps.newHashMap();
        //设置请求的参数
        params.entrySet().forEach(param -> {
            if (param.getKey().startsWith("file") && new File(param.getValue().toString()).isFile()) {
                paramsPart.put(param.getKey().split("_")[1], new FileBody(new File(param.getValue().toString())));
            }else {
                paramsPart.put(param.getKey(), new StringBody(param.getValue().toString(), ContentType.create("application/json", Consts.UTF_8)));
            }
        });
        //拼接HttpEntity参数
        MultipartEntityBuilder requestEntityBuilder = MultipartEntityBuilder.create();
        paramsPart.entrySet().forEach(requestParam -> requestEntityBuilder.addPart(requestParam.getKey(), requestParam.getValue()));
        return requestEntityBuilder.build();
    }

}
