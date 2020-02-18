package com.hlxd.microcloud.filter;


import com.alibaba.fastjson.JSONObject;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.util.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/11/1516:23
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT cloud
 */
@Component
@Slf4j
public class AuthorizedFilter implements Filter {

    private List<String> whitelist = new ArrayList<String>();
    private List<String> regexlist = new ArrayList<String>();
    private static final String _JSON_CONTENT = "application/json; charset=UTF-8";
    private static final String _HTML_CONTENT = "text/html; charset=UTF-8";
    private static final String _403_JSON = "{'code': '403', 'msg': '访问被拒绝，客户端未授权！'}";
    private static final String _403_HTML = "<html><body><div style='text-align:center'><h1 style='margin-top: 10px;'>403 Forbidden!</h1><hr><span>@lichmama</span></div></body></html>";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        if(!url.equals("/demo/user/user/login")){
            String Authorization = request.getHeader("Authorization");
            Jedis jedis = JedisPoolUtils.getJedis();
            String auth = jedis.get(Authorization);//获取用户信息
            UserInfo userInfo = JSONObject.parseObject(auth,UserInfo.class);//将json对象转成java对象
            String[] permission = userInfo.getPermissions();
            boolean flag = false;//需要放开所有权限可以在这里设置为true,下面判断内容省略
            for(String s:permission){
                if(s.equals(url)){
                    flag = true;
                }
            }
            if(!flag){
                response.setStatus(403);
                log.info("访问为授权方法！");
                response.setContentType(_HTML_CONTENT);
                response.getWriter().print(_403_HTML);
            }else{
                filterChain.doFilter(request,response);
            }
            //System.out.println(userInfo.toString());
        }else{
            filterChain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
