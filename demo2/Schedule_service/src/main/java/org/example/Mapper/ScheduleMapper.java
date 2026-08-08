package org.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Entity.Schedule;

@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
}
