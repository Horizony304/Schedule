package com.hearts.schedule.dao.impl;

import com.hearts.schedule.dao.BaseDao;
import com.hearts.schedule.dao.ScheduleDao;
import com.hearts.schedule.pojo.Schedule;

import java.util.List;

public class ScheduleDaoImpl extends BaseDao implements ScheduleDao
{
    @Override
    public List<Schedule> findItemListByUid(int uid)
    {
        String sql = "select sid, uid, title, completed from schedule where uid = ?";
        return baseQuery(Schedule.class, sql, uid);
    }

    @Override
    public int addDefaultItem(int uid)
    {
        String sql = "insert into schedule values(null, ?, '请输入日程', 0)";
        return baseUpdate(sql, uid);
    }

    @Override
    public int updateSchedule(Schedule schedule)
    {
        String sql = "update schedule set title = ?, completed = ? where sid = ?";
        return baseUpdate(sql, schedule.getTitle(), schedule.getCompleted(), schedule.getSid());
    }

    @Override
    public int removeSchedule(int sid)
    {
        String sql = "delete from schedule where sid = ?";
        return baseUpdate(sql, sid);
    }
}
