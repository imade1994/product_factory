package com.hlxd.oauthorization.service;
/**
 * @Author Administrator
 * @Date 2018/7/20 14:17
 */

import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.oauthorization.fallback.RemoteUserServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0;
 * @Date： 2018-07-20  14:17
 * @Description：
 * @FeignClient(name = "auth",url = "http://127.0.0.1:9002")
 **/


@FeignClient(name = "user-server",fallback = RemoteUserServiceFallbackImpl.class )
public interface RemoteUserService {
    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    R<UserInfo> getUser(@RequestParam(value = "userName", required = false) String username,
                        @RequestParam(value = "passWord", required = false) String password);
}
