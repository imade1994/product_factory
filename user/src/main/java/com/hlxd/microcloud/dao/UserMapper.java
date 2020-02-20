package com.hlxd.microcloud.dao;



import com.hlxd.microcloud.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-02-19
 */
public interface UserMapper {
    SysUser getUserInfo(@Param("username")String name, @Param("password")String password);

}
