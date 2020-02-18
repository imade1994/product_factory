package com.hlxd.microcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @Program：ouyaaa-microcloud
 * @Author：taojun
 * @Version：1.0
 * @Date： 2018-07-20  14:26
 * @Description：
 **/

@Data
public class UserInfo implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 用户基本信息
     */
    private SysUser sysUser;


    private String[] permissions;

    private String[] roles;


    private String[] menus;





}
