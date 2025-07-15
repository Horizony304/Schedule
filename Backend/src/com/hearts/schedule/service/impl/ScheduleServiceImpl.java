package com.hearts.schedule.service.impl;

import com.hearts.schedule.dao.ScheduleDao;
import com.hearts.schedule.dao.impl.ScheduleDaoImpl;
import com.hearts.schedule.pojo.Schedule;
import com.hearts.schedule.service.ScheduleService;

import java.util.List;

public class ScheduleServiceImpl implements ScheduleService
{
    private ScheduleDao scheduleDao = new ScheduleDaoImpl();

    @Override
    public List<Schedule> findItemListByUid(int uid)
    {
        return scheduleDao.findItemListByUid(uid);
    }

    @Override
    public int addDefaultItem(int uid)
    {
        return scheduleDao.addDefaultItem(uid);
    }

    @Override
    public int updateSchedule(Schedule schedule)
    {
        return scheduleDao.updateSchedule(schedule);
    }

    @Override
    public int removeSchedule(int sid)
    {
        return scheduleDao.removeSchedule(sid);
    }
}
