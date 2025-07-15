package com.hearts.schedule.controller;

import com.hearts.schedule.common.Result;
import com.hearts.schedule.pojo.Schedule;
import com.hearts.schedule.service.ScheduleService;
import com.hearts.schedule.service.impl.ScheduleServiceImpl;
import com.hearts.schedule.util.WebUtil;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/schedule/*")
public class ScheduleController extends BaseController
{
    private ScheduleService scheduleService = new ScheduleServiceImpl();

    /**
     * 查询所有日程的业务处理方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int uid = Integer.parseInt(req.getParameter("uid"));
        // 调用服务层方法，查询所有日程
        List<Schedule> itemList = scheduleService.findItemListByUid(uid);
        // 将日程信息装入result，转换为JSON响应给客户端
        Map<String, Object> data = new HashMap<>();
        data.put("itemList", itemList);
        WebUtil.writeJson(resp, Result.ok(data));
    }

    /**
     * 向数据库中增加一个新的默认数据的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addDefaultSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int uid = Integer.parseInt(req.getParameter("uid"));
        // 调用服务层方法，为当前用户新增一个默认空数据
        scheduleService.addDefaultItem(uid);
        WebUtil.writeJson(resp, Result.ok(null));
    }

    /**
     * 更新日程的业务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Schedule schedule = WebUtil.readJson(req, Schedule.class);
        // 调用服务层方法，更新日程
        scheduleService.updateSchedule(schedule);
        // 响应成功
        WebUtil.writeJson(resp, Result.ok(null));
    }

    /**
     * 删除日程的业务
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void removeSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // 获取要删除日程的id
        int sid = Integer.parseInt(req.getParameter("sid"));
        // 调用服务层方法，删除日程
        scheduleService.removeSchedule(sid);
        // 响应
        WebUtil.writeJson(resp, Result.ok(null));
    }
}
