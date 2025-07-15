package com.hearts.schedule.service;

import com.hearts.schedule.pojo.Schedule;

import java.util.List;

public interface ScheduleService
{
    /**
     * 用户查询所有日程的方法
     * @param uid 要查询的uid
     * @return 查询到的日程列表
     */
    List<Schedule> findItemListByUid(int uid);

    /**
     * 增加一条默认日程项
     * @param uid
     */
    int addDefaultItem(int uid);

    /**
     * 更新一条日程
     * @param schedule
     */
    int updateSchedule(Schedule schedule);

    /**
     * 删除一条日程
     * @param sid
     */
    int removeSchedule(int sid);
}
