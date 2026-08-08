package org.example.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.Entity.Schedule;
import org.example.HttpResult;
import org.example.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
@Api(tags = "排片管理接口")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/list")
    @ApiOperation("获取排片列表")
    public HttpResult list(){
        List<Schedule> schedules = scheduleService.findAll();

        if(schedules != null){
            return HttpResult.successResult(schedules);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/page")
    @ApiOperation("分页查询排片")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") @RequestBody Schedule schedule){
        IPage<Schedule> buyIPage = scheduleService.findByPage(npage, schedule);

        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/insert")
    @ApiOperation("新增排片")
    public HttpResult insert(@ApiParam("排片信息") @RequestBody Schedule schedule){
        scheduleService.add(schedule);
        return HttpResult.successResult("添加成功");
    }

    @RequestMapping("/update")
    @ApiOperation("更新排片信息")
    public HttpResult update(@ApiParam("排片信息") @RequestBody Schedule schedule){
        scheduleService.update(schedule);
        return HttpResult.successResult("修改成功");
    }

    @RequestMapping("/delete")
    @ApiOperation("删除排片")
    public HttpResult delete(@ApiParam("排片ID") Integer id){
        scheduleService.delete(id);
        return HttpResult.successResult("删除成功");
    }

    // ① 查单个排片（含余座）— 下单前校验用
    @GetMapping("/detail/{id}")
    public HttpResult detail(@PathVariable Integer id){
        return HttpResult.successResult(scheduleService.findById(id));
    }

    // ② 扣座位 — 下单成功时调
    @PostMapping("/deduct")
    public HttpResult deduct(@RequestParam Integer scheduleId){
        scheduleService.deductSeats(scheduleId);
        return HttpResult.successResult("扣减成功");
    }

    // ③ 恢复座位 — 取消/退款/超时取消时调
    @PostMapping("/restore")
    public HttpResult restore(@RequestParam Integer scheduleId, @RequestParam Integer count){
        scheduleService.restoreSeats(scheduleId, count);
        return HttpResult.successResult("恢复成功");
    }
}
