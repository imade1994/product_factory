package com.hlxd.microcloud.entity;
/**
 * @Author Administrator
 * @Date 2018/7/20 14:27
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  id;
    /**
     * 用户名
     */
    private String username;

    private String password;
    /**
     * 随机盐
     */
    @JsonIgnore
    private String salt;
    /**
     * 组织代码
     */
    private String organizeCode;
    /**
     * 组织名
     */
    private String organizeName;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 授权API
     */
    private String grantApi;

}
