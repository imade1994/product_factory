package com.hlxd.microcloud.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/4/810:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory
 */
public class DruidUtil {
    // 私有成员 DataSource
    private static DataSource ds;
    static {
        try {
            // 加载配置文件
            Properties pro = new Properties();
            pro.load(DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            // 获取 DataSource 对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    /**
     * 获取数据库连接池对象
     * @return
     */
    public static DataSource getDataSource() {
        return ds;
    }
    /**
     * 释放数据库资源
     * @param rs
     * @param sta
     * @param conn
     */
    public static void close(ResultSet rs, Statement sta, Connection conn) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != sta) {
            try {
                sta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn) {
            try {
                conn.close();       // 归还数据库连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 释放数据库连接资源
     * @param sta
     * @param conn
     */
    public static void close(Statement sta, Connection conn) {
        close(null, sta, conn);
    }
}
