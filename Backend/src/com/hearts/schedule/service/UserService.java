package com.hearts.schedule.service;

import com.hearts.schedule.pojo.User;

public interface UserService
{
    /**
     * 用户完成注册的业务方法
     * @param registerUser 用于保存注册用户对象
     * @return 注册成功返回大于0的整数，否则返回0
     */
    int register(User registerUser);

    /**
     * 根据用户名获得完整用户信息的方法
     * @param username 要查询的用户名
     * @return 若找到则返回User对象，否则返回null
     */
    User findByUsername(String username);
}
