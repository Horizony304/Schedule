package com.hearts.schedule.dao.impl;

import com.hearts.schedule.dao.BaseDao;
import com.hearts.schedule.dao.UserDao;
import com.hearts.schedule.pojo.User;

import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao
{
    @Override
    public int addUser(User user)
    {
        String sql = "insert into user values(null, ?, ?)";
        return baseUpdate(sql, user.getUsername(), user.getUserpwd());
    }

    @Override
    public User findByUsername(String username)
    {
        String sql = "select uid, username, userpwd from user where username = ?";
        List<User> userList = baseQuery(User.class, sql, username);
        return userList != null && userList.size() > 0 ? userList.get(0) : null;
    }
}
