package org.example.Service.imp;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.DateUtils;
import org.example.Entity.Schedule;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.example.Mapper.ScheduleMapper;
import org.example.Service.ScheduleService;
import org.example.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceI implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> findAll() {
        return scheduleMapper.selectList(null);
    }

    @Override
    @AutoFill(OperationType.INSERT)
    public void add(Schedule schedule) {
        scheduleMapper.insert(schedule);
    }

    @Override
    public Schedule findById(Integer id) {
        return scheduleMapper.selectById(id);
    }

    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(Schedule schedule) {
        scheduleMapper.updateById(schedule);
    }

    @Override
    public void formupdate(Schedule schedule) {


        schedule.setScheduleUpdateTime(DateUtils.format(new Date()));

        scheduleMapper.updateById(schedule);
    }

    //检测是否还有号
    @Override
    public Boolean isseatNum(Integer scheduleId){

        Schedule schedule= scheduleMapper.selectById(scheduleId);

        if(schedule == null){
            return false;
        }else{
            if(schedule.getScheduleRemainseats() == 0){
                return false;
            }else{
                schedule.setScheduleRemainseats(schedule.getScheduleRemainseats()-1);
                return true;
            }
        }

    }

    @Override
    public void delete(Integer id) {
        scheduleMapper.deleteById(id);
    }

    @Override
    public IPage<Schedule> findByPage(Integer npage, Schedule schedule) {

        IPage page = new Page(npage, 10);

        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(schedule.getScheduleMovieName()), "schedule_movie_name", schedule.getScheduleMovieName());

        queryWrapper.like(!StringUtils.isEmpty(schedule.getScheduleCinemaName()), "schedule_cinema_name", schedule.getScheduleCinemaName());
        return scheduleMapper.selectPage(page, queryWrapper);


    }

    //扣座功能
    public void deductSeats(Integer scheduleId) {
        Schedule s = scheduleMapper.selectById(scheduleId);
        if (s == null) {
            throw new BusinessException("排片不存在");
        }
        if (s.getScheduleRemainseats() == null || s.getScheduleRemainseats() <= 0) {
            throw new BusinessException("该片场没有余座");
        }
        s.setScheduleRemainseats(s.getScheduleRemainseats() - 1);
        scheduleMapper.updateById(s);
    }

    //恢复座位（超时，人为取消，退款
    public void restoreSeats(Integer scheduleId, Integer count) {
        Schedule s = scheduleMapper.selectById(scheduleId);
        if (s == null) {
            throw new BusinessException("排片不存在");
        }
        s.setScheduleRemainseats(s.getScheduleRemainseats() + count);
        scheduleMapper.updateById(s);
    }


}
