package com.hlxd.microcloud.config;/**
 * @Author Administrator
 * @Date 2018/7/23 16:08
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-23  16:08
 * @Description：
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                //.antMatchers()
                //.antMatchers("/*/**")
                .antMatchers("/user/login","/user/userInfo","/send/*")//登陆接口免验证
                .permitAll()
                //.permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable() ;
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        super.configure(resources);
    }
}
