package org.example.feign;

import io.swagger.annotations.ApiOperation;
import org.example.Entity.Schedule;
import org.example.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient("schedule-service")
public interface ScheduleFeign {

    @GetMapping("/schedule/detail/{id}")
    public HttpResult getSchedule(@PathVariable("id") Integer id);

    @PostMapping("/schedule/deduct")
    public HttpResult deduct(@RequestParam("scheduleId") Integer scheduleId);

    @PostMapping("/schedule/restore")
    public HttpResult restore(@RequestParam("scheduleId") Integer scheduleId, @RequestParam("count") Integer count);

    @RequestMapping("schedule/list")
    public HttpResult list();
}