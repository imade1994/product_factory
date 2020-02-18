package com.hlxd.oauthorization.properties;/**
 * @Author Administrator
 * @Date 2018/7/20 14:01
 */

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-20  14:01
 * @Description：
 **/

public class OAuthProperties {

    private String jwtSigningKey = "hlxd";

    private OAuthClientProperties[] clients = {};

    public OAuthClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuthClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
