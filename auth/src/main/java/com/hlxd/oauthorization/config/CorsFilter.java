package com.hlxd.oauthorization.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/3/2011:18
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT hlxdmicrocloud
 */
@Component
public class CorsFilter implements Filter {
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);



    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        //HttpServletRequest reqs = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(req,res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
