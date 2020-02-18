package com.hlxd.oauthorization.config;

import com.hlxd.oauthorization.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.data.redis.connection.RedisConnectionFactory;
/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-20  14:07
 * @Description：
 **/

@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /*
     * 配置存储至Redis内
     * */
    @Bean
    @ConditionalOnProperty(prefix = "ouyaaa.security.oauth", name = "storeType",havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /*
     * JWT设置
     * @ConditionalOnProperty(prefix = "ouyaaa.security.oauth2", name = "storeType",havingValue = "jwt",matchIfMissing = true)
     * 当配置文件前缀是mirocloud.security.oauth2  最后名是storeType 等于jwt该文件配置生效，matchIfMissing 默认写的情况下也生效
     * */
    @Configuration
    @ConditionalOnProperty(prefix = "ouyaaa.security.oauth", name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfig{

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        /*
         * JWT的密签
         * */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(securityProperties.getOauth().getJwtSigningKey());
            return accessTokenConverter;
        }

        /*
         * 添加自定义信息加入jwt中
         * */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }
}
