package com.hearts.schedule.dao;

import com.hearts.schedule.util.JDBCUtil;

import javax.sql.rowset.JdbcRowSet;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseDao
{
    // 公共的查询方法 返回的是单个对象
    public <T> T baseQueryObject(Class<T> clazz, String sql, Object... args)
    {
        T t = null;
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            // 准备语句对象
            preparedStatement = connection.prepareStatement(sql);
            // 设置语句上的参数
            for (int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i + 1, args[i]);
            }
            // 执行查询
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                t = (T) resultSet.getObject(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            if (resultSet != null)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            JDBCUtil.releaseConnection();
        }
        return t;
    }

    // 公共的查询方法 返回的是对象的集合
    public <T> List<T> baseQuery(Class<T> clazz, String sql, Object... args)
    {
        List<T> list = new ArrayList<>();
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        try
        {
            // 准备语句对象
            preparedStatement = connection.prepareStatement(sql);
            // 设置语句上的参数
            for (int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i + 1, args[i]);
            }
            // 执行查询
            resultset = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultset.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 将结果集通过反射封装成实体类对象
            while (resultset.next())
            {
                // 使用反射实例化对象
                Object obj = clazz.getDeclaredConstructor().newInstance();

                for (int i = 1; i <= columnCount; i++)
                {
                    String columnLabel = metaData.getColumnLabel(i);
                    Object value = resultset.getObject(columnLabel);
                    // 处理datetime类型字段和java.util.Date转换问题
                    if (value.getClass().equals(LocalDateTime.class))
                    {
                        value = Timestamp.valueOf((LocalDateTime) value);
                    }
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(obj, value);
                }

                list.add((T) obj);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (resultset != null)
            {
                try
                {
                    resultset.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            JDBCUtil.releaseConnection();
        }
        return list;
    }

    // 通用的增删改方法
    public int baseUpdate(String sql, Object... args)
    {
        // 获取连接
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try
        {
            // 准备语句对象
            preparedStatement = connection.prepareStatement(sql);
            // 设置语句上的参数
            for (int i = 0; i < args.length; i++)
            {
                preparedStatement.setObject(i + 1, args[i]);
            }
            // 执行增删改 executeUpdate
            rows = preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (preparedStatement != null)
            {
                try
                {
                    preparedStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            JDBCUtil.releaseConnection();
        }
        // 返回的是影响数据库的行数
        return rows;
    }
}
