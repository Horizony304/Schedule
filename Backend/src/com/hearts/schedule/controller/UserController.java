package com.hearts.schedule.controller;

import com.hearts.schedule.common.Result;
import com.hearts.schedule.common.ResultCodeEnum;
import com.hearts.schedule.pojo.User;
import com.hearts.schedule.service.UserService;
import com.hearts.schedule.service.impl.UserServiceImpl;
import com.hearts.schedule.util.MD5Util;
import com.hearts.schedule.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserController extends BaseController
{
    private UserService userService = new UserServiceImpl();

    /**
     * 接收用户注册请求的业务处理方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // 接收要注册的用户信息
        User registerUser = WebUtil.readJson(req, User.class);
        // 调用服务层方法，将用户注册进入数据库
        int rows = userService.register(registerUser);
        Result result = null;
        if (rows > 0)
        {
            result = Result.ok(null);
        }
        else
        {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     * 接收用户登录请求的业务处理方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // 接收要登录的用户信息
        User inputUser = WebUtil.readJson(req, User.class);
        // 调用服务层方法，根据用户名查询数据库中是否存在该用户
        User loginUser = userService.findByUsername(inputUser.getUsername());
        Result result = null;

        if (loginUser == null)
        {
            // 没有根据用户名找到用户，说明用户名有误
            result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        // 判断密码是否匹配
        else if (!loginUser.getUserpwd().equals(MD5Util.encrypt(inputUser.getUserpwd())))
        {
            // 密码有误
            result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        else
        {
            // 登录成功，将用户信息存入Session
            req.getSession().setAttribute("user", loginUser);
            // 将密码清空后，将用户信息响应给客户端
            loginUser.setUserpwd("");
            Map<String, Object> data = new HashMap<>();
            data.put("loginUser", loginUser);
            result = Result.ok(data);
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     * 注册时校验用户名是否被占用的业务接口
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        User registerUser = userService.findByUsername(username);
        // 封装结果对象
        Result result = null;
        if (registerUser == null)
        {
            // 未占用,创建一个code为200的对象
            result = Result.ok(null);
        }
        else
        {
            // 占用, 创建一个结果为505的对象
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        // 将result对象转换成JSON并响应给客户端
        WebUtil.writeJson(resp, result);
    }
}
