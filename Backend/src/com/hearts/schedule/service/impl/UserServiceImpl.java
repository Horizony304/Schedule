package com.hearts.schedule.service.impl;

import com.hearts.schedule.dao.UserDao;
import com.hearts.schedule.dao.impl.UserDaoImpl;
import com.hearts.schedule.pojo.User;
import com.hearts.schedule.service.UserService;
import com.hearts.schedule.util.MD5Util;

public class UserServiceImpl implements UserService
{
    private UserDao userDao = new UserDaoImpl();

    @Override
    public int register(User registerUser)
    {
        // 1.将用户的明文密码转换为密文密码
        registerUser.setUserpwd(MD5Util.encrypt(registerUser.getUserpwd()));
        // 2.调用DAO层的方法，将registerUser的信息存入数据库
        return userDao.addUser(registerUser);
    }

    @Override
    public User findByUsername(String username)
    {
        // 调用DAO层的方法，继续查询
        return userDao.findByUsername(username);
    }
}
