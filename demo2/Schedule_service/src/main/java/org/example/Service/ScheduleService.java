package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Schedule;

import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll();

    IPage<Schedule> findByPage(Integer npage, Schedule schedule);

    void add(Schedule schedule);

    Schedule findById(Integer id);

    void update(Schedule schedule);

    void formupdate(Schedule schedule);

    void delete(Integer id);

    Boolean isseatNum(Integer scheduleId);

    void deductSeats(Integer scheduleId);

    void restoreSeats(Integer scheduleId, Integer count);

}
