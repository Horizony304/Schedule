package com.hearts.schedule.dao;

import com.hearts.schedule.pojo.User;

public interface UserDao
{
    /**
     * 向数据库中增加一条用户记录的方法
     * @param user 要增加的用户对象
     * @return 增加成功返回1，增加失败返回0
     */
    int addUser(User user);

    /**
     * 根据用户名在数据库中查找用户
     * @param username 要查询的用户名
     * @return 若找到则返回User对象，否则返回null
     */
    User findByUsername(String username);
}
