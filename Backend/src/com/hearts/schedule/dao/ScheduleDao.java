package com.hearts.schedule.dao;

import com.hearts.schedule.pojo.Schedule;

import java.util.List;

public interface ScheduleDao
{
    /**
     * 根据uid查询所有日程
     * @param uid 待查询的uid
     * @return 查询到的日程列表
     */
    List<Schedule> findItemListByUid(int uid);

    /**
     * 根据uid在数据库新增一条默认日程
     * @param uid 待新增的用户uid
     * @return 大于0则增加成功
     */
    int addDefaultItem(int uid);

    /**
     * 更新一条日程
     * @param schedule 待更新的日程
     * @return 大于0则更新成功
     */
    int updateSchedule(Schedule schedule);

    /**
     * 删除一条日程
     * @param sid 待删除的日程id
     * @return 大于0则删除成功
     */
    int removeSchedule(int sid);
}
