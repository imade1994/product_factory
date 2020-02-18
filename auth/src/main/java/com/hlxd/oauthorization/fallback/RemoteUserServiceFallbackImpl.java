package com.hlxd.oauthorization.fallback;/**
 * @Author Administrator
 * @Date 2018/7/23 0:23
 */


import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.UserInfo;
import org.springframework.stereotype.Component;

import com.hlxd.oauthorization.service.RemoteUserService;

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-23  00:23
 * @Description：
 **/

@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService{

    @Override
    public R<UserInfo> getUser(String username, String password) {
        //log.error("feign 查询用户信息失败:{}", username);
        return null;
    }
}
