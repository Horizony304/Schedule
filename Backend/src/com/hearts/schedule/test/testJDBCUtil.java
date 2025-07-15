package com.hearts.schedule.test;

import com.hearts.schedule.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

public class testJDBCUtil
{
    @Test
    public void testJDBCConnection()
    {
        Connection connection = JDBCUtil.getConnection();
        System.out.println("connection = " + connection);
    }
}
