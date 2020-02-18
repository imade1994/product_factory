package com.hlxd.oauthorization.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.SysUser;
import com.hlxd.microcloud.entity.UserInfo;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hlxd.oauthorization.service.RemoteUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        R<UserInfo> result = remoteUserService.getUser(request.getParameter("username"),request.getParameter("password"));
        if (result == null || result.getData() ==null ){
            throw  new UsernameNotFoundException("用户不存在");
        }
        UserInfo user = result.getData();
        Set<String> dbOauthSet = new HashSet<>();

        if (ArrayUtils.isNotEmpty(user.getRoles())){
            // 获取角色
            Arrays.stream(user.getRoles()).forEach(role -> dbOauthSet.add(role));
            //获取资源
            dbOauthSet.addAll(Arrays.asList(user.getPermissions()));
        }

        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbOauthSet.toArray(new String[0]));

        SysUser sysUser = user.getSysUser();


        //boolean enabled = StringUtils.equals(sysUser.getDelFlag(),"0");

        /*
         * 构造security用户
         * */
        return new User(sysUser.getUsername(),request.getParameter("password"),true,true,true,true,authorities);
    }
}
