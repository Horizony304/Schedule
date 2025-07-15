package com.hearts.schedule.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class JDBCUtil
{
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static DataSource dataSource;

    // 初始化连接池
    static
    {
        // 读取配置文件
        Properties properties = new Properties();
        InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try
        {
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // 1.向外提供连接池的方法
    public static DataSource getDataSource()
    {
        return dataSource;
    }

    // 2.向外提供连接的方法
    public static Connection getConnection()
    {
        Connection connection = threadLocal.get();
        if (connection == null)
        {
            try
            {
                connection = dataSource.getConnection();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
            threadLocal.set(connection);
        }
        return connection;
    }

    // 3.向外提供回收连接的方法
    public static void releaseConnection()
    {
        Connection connection = threadLocal.get();
        if (connection != null)
        {
            threadLocal.remove();
            try
            {
                // 把连接设置回自动提交的连接
                connection.setAutoCommit(true);
                // 自动归还到连接池
                connection.close();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}