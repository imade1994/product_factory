package com.hlxd.oauthorization.properties;/**
 * @Author Administrator
 * @Date 2018/7/20 14:00
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-20  14:00
 * @Description：
 **/



@ConfigurationProperties(prefix = "ouyaaa.security")
public class SecurityProperties {

    private OAuthProperties oauth = new OAuthProperties();

    public OAuthProperties getOauth() {
        return oauth;
    }

    public void setOauth(OAuthProperties oauth) {
        this.oauth = oauth;
    }
}
