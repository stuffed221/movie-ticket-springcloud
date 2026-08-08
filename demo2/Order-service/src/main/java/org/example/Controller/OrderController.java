package org.example.Controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.dto.OrderCreatDTO;
import org.example.dto.OrderPayDTO;
import org.example.DateUtils;
import org.example.Entity.Order;
import org.example.HttpResult;
import org.example.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(tags = "订单管理接口")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private org.example.feign.ScheduleFeign scheduleFeign;

    @GetMapping("/list")
    @ApiOperation("获取订单列表")
    public HttpResult list(@ApiParam("页码") Integer page, @ApiParam("用户ID") @RequestParam(required = false) Integer userId){
        List<Order> order;
        if (userId != null) {
            order = orderService.findByUserId(userId);
        } else {
            order = orderService.findAll();
        }

        if(order != null){

            return HttpResult.successResult(order);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/page")
    @ApiOperation("分页查询订单")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") Order order){
        IPage<Order> buyIPage = orderService.findByPage(npage, order);
        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }


    }

    @GetMapping("/count")
    @ApiOperation("统计订单数量")
    public HttpResult count(){
        return HttpResult.successResult(orderService.count());
    }

    @RequestMapping("/insert")
    @ApiOperation("新增订单")
    public HttpResult insert(@ApiParam("订单信息") Order order){
        orderService.add(order);
        return HttpResult.successResult("添加成功");
    }

    @RequestMapping("/update")
    @ApiOperation("更新订单信息")
    public HttpResult update(@ApiParam("订单信息") Order order){
        order.setOrderUpdateTime(DateUtils.format(new Date()));
        orderService.update(order);
        return HttpResult.successResult("修改成功");
    }

    @RequestMapping("/delete")
    @ApiOperation("删除订单")
    public HttpResult delete(@ApiParam("订单ID") Integer id){
        orderService.delete(id);
        return HttpResult.successResult("删除成功");
    }

    @SentinelResource(value = "createOrder", blockHandler = "createOrderBlock")
    @PostMapping("/creatord")
    @ApiOperation("创建订单")
    public HttpResult creatord(@ApiParam("订单创建封装体") @Validated @RequestBody OrderCreatDTO orderCreatDTO){

        if(scheduleFeign.getSchedule(orderCreatDTO.getOrdScheduleId()).getData() != null){
            Order order = new Order();
            order.setOrderNo(orderCreatDTO.getOrdNo());
            order.setOrderScheduleId(orderCreatDTO.getOrdScheduleId());
            order.setOrderUserId(orderCreatDTO.getOrdUserId());
            order.setOrderSeat(String.valueOf(orderCreatDTO.getOrdSeat()));
            order.setOrderStatus(orderCreatDTO.getOrdStatus());

            orderService.add(order);

            return HttpResult.successResult("添加成功");
        }else{
            return HttpResult.errorResult("该片场没有余座");
        }



    }

    // sentinel兜底,被限流时执行
    public HttpResult createOrderBlock(OrderCreatDTO dto, BlockException e) {
        return HttpResult.errorResult("系统繁忙，请稍后再试");
    }

    @PostMapping("/pay")
    @ApiOperation("支付订单")
    public HttpResult pay(@ApiParam("支付前端封装体") @Validated @RequestBody OrderPayDTO orderPayDTO){
        orderService.pay(orderPayDTO.getOrderId(), orderPayDTO.getPayMethod(), orderPayDTO.getPayAmount());
        return HttpResult.successResult("支付成功");
    }

    @PostMapping("/complete")
    @ApiOperation("完成订单")
    public HttpResult complete(@ApiParam("订单ID") @RequestParam Integer orderId){
        orderService.complete(orderId);
        return HttpResult.successResult("核销成功");
    }

    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    public HttpResult cancel(@ApiParam("订单ID") @RequestParam Integer orderId,@ApiParam("取消原因") @RequestParam(defaultValue = "用户取消") String reason){
        orderService.cancel(orderId, reason);
        return HttpResult.successResult("取消成功");
    }

    @PostMapping("/refund")
    @ApiOperation("退款")
    public HttpResult refund(@ApiParam("订单ID") @RequestParam Integer orderId){
        orderService.refund(orderId);
        return HttpResult.successResult("退款成功");
    }

}
