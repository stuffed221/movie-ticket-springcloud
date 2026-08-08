package org.example.Service.imp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.DateUtils;
import org.example.Entity.Order;
import org.example.Entity.Schedule;
import org.example.Mapper.OrderMapper;
import org.example.Service.OrderService;
import org.example.exception.BusinessException;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import io.seata.spring.annotation.GlobalTransactional;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceI implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private org.example.feign.ScheduleFeign scheduleFeign;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RBloomFilter<String> seatBloomFilter;

    @PostConstruct //在依赖注入后初始化布隆过滤器
    public void initBloomFilter() {
        seatBloomFilter = redissonClient.getBloomFilter("seat:bloom");
        seatBloomFilter.delete();
        seatBloomFilter.tryInit(10000, 0.03);

        @SuppressWarnings("unchecked")
        List<Schedule> schedules = JSON.parseObject(
                JSON.toJSONString(scheduleFeign.list().getData()),
                new TypeReference<List<Schedule>>() {});
        for (Schedule s : schedules) {
            for (int i = 1; i <= 40; i++) {
                seatBloomFilter.add("seat:" + s.getScheduleId() + ":" + i);
            }
        }
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.selectList(null);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("ord_user_id", userId);
        return orderMapper.selectList(qw);
    }

    @Override
    public IPage<Order> findByPage(Integer npage, Order order) {
        IPage page = new Page(npage, 10);

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!StringUtils.isEmpty(order.getOrderMovieName()),"order_movie_name",order.getOrderMovieName() );
        queryWrapper.like(!StringUtils.isEmpty(order.getOrderCinemaName()),"order_cinema_name",order.getOrderCinemaName() );


        return orderMapper.selectPage(page, queryWrapper);
    }

    @Override
    @GlobalTransactional
    @AutoFill(OperationType.INSERT)
    public void add(Order order) {

        // 幂等性防护
        if (order.getOrderNo() != null) {
            String tokenKey = "idempotent:order:" + order.getOrderNo();
            Boolean ok = redisTemplate.opsForValue().setIfAbsent(tokenKey, "1", 10, TimeUnit.MINUTES);
            if (Boolean.FALSE.equals(ok)) {
                throw new BusinessException("请勿重复提交");
            }
        }


        if (!seatBloomFilter.contains("seat:" + order.getOrderScheduleId() + ":" + order.getOrderSeat())) {
            throw new BusinessException("座位不存在");
        }


        if (StringUtils.isEmpty(order.getOrderNo())) {
            order.setOrderNo("NO_" + System.currentTimeMillis());
        }
        order.setOrderStatus(0);

        Schedule schedule = JSON.parseObject(
                JSON.toJSONString(scheduleFeign.getSchedule(order.getOrderScheduleId()).getData()),
                Schedule.class);
        if (schedule == null) {
            throw new BusinessException("排片不存在");
        }
        if (schedule.getScheduleRemainseats() == null || schedule.getScheduleRemainseats() <= 0) {
            throw new BusinessException("该片场没有余座");
        }

        order.setOrderMovieName(schedule.getScheduleMovieName());
        order.setOrderCinemaName(schedule.getScheduleCinemaName());
        order.setOrderHall(schedule.getScheduleHall());
        // 选座加锁
        String now = DateUtils.format(new Date());
        order.setOrderCreateTime(now);
        order.setOrderUpdateTime(now);
        order.setOrderPayDeadline(DateUtils.format(new Date(System.currentTimeMillis() + 15 * 60 * 1000)));

        RLock lock = redissonClient.getLock("seat:" + order.getOrderScheduleId() + ":" + order.getOrderSeat());
        try {
            if (lock.tryLock(3, 30, TimeUnit.SECONDS)) {
                try {
                    scheduleFeign.deduct(order.getOrderScheduleId());
                    orderMapper.insert(order);
                } finally {
                    lock.unlock();
                }
            } else {
                throw new BusinessException("座位已被抢，请重选");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            throw new BusinessException("系统繁忙，请重试");
        }


    }



    @Override
    public Order findById(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public void delete(Integer integer) {
        orderMapper.deleteById(integer);
    }

    @Override
    public Integer count() {
        return orderMapper.selectCount(
                new QueryWrapper<Order>().in("ord_status", 1, 2)
        );
    }

    //null覆盖问题所以用updatewrapper
    @Override
    public void pay(Integer orderId, Integer payMethod,Integer payamount) {
        // 幂等性防护
        String payKey = "pay:" + orderId;
        Boolean ok = redisTemplate.opsForValue().setIfAbsent(payKey, "1", 5, TimeUnit.MINUTES);
        if (Boolean.FALSE.equals(ok)) {
            throw new BusinessException("订单处理中，请勿重复支付");
        }

        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getOrderStatus() != 0) {
            return;
        }
        String now = DateUtils.format(new Date());
        UpdateWrapper<Order> uw = new UpdateWrapper<>();
        uw.eq("ord_id", orderId);
        uw.set("ord_status", 1);
        uw.set("order_amount", payamount);
        uw.set("ord_pay_time", now);
        uw.set("ord_pay_deadline", null);
        uw.set("ord_pay_method", payMethod != null ? payMethod : 1);
        uw.set("ord_update_time", now);
        orderMapper.update(null, uw);
    }

    @Override
    public void complete(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getOrderStatus() != 1) {
            return;
        }
        order.setOrderStatus(2);
        order.setOrderCompletelTime(DateUtils.format(new Date()));
        order.setOrderUpdateTime(DateUtils.format(new Date()));
        orderMapper.updateById(order);
    }


    @Override
    public void cancel(Integer orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || (order.getOrderStatus() != 0 && order.getOrderStatus() != 1)) {
            return;
        }
        String now = DateUtils.format(new Date());
        UpdateWrapper<Order> uw = new UpdateWrapper<>();
        uw.eq("ord_id", orderId);
        uw.set("ord_status", 3);
        uw.set("ord_cancel_time", now);
        uw.set("ord_cancel_reason", StringUtils.isEmpty(reason) ? "用户取消" : reason);
        uw.set("ord_pay_deadline", null);
        uw.set("order_amount", null);
        uw.set("ord_pay_method", null);
        uw.set("ord_update_time", now);
        orderMapper.update(null, uw);

        Schedule schedule = JSON.parseObject(
                JSON.toJSONString(scheduleFeign.getSchedule(order.getOrderScheduleId()).getData()),
                Schedule.class);
        if (schedule != null) {
            int seatCount = order.getOrderSeat() != null
                ? order.getOrderSeat().split(",").length
                : 1;
            scheduleFeign.restore(order.getOrderScheduleId(),seatCount);
        }
    }

    @Override
    public void refund(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getOrderStatus() != 1) {
            return;
        }
        String now = DateUtils.format(new Date());
        UpdateWrapper<Order> uw = new UpdateWrapper<>();
        uw.eq("ord_id", orderId);
        uw.set("ord_status", 4);
        uw.set("ord_cancel_time", now);
        uw.set("ord_cancel_reason", "用户退款");
        uw.set("order_amount", null);
        uw.set("ord_pay_method", null);
        uw.set("ord_update_time", now);
        orderMapper.update(null, uw);

        Schedule schedule = JSON.parseObject(
                JSON.toJSONString(scheduleFeign.getSchedule(order.getOrderScheduleId()).getData()),
                Schedule.class);
        if (schedule != null) {
            int seatCount = order.getOrderSeat() != null
                ? order.getOrderSeat().split(",").length
                : 1;
            scheduleFeign.restore(order.getOrderScheduleId(),seatCount);
        }
    }

    @Override
    public void cancelExpired() {
        String now = DateUtils.format(new Date());
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("ord_status", 0)
          .lt("ord_pay_deadline", now);
        List<Order> expiredOrders = orderMapper.selectList(qw);
        for (Order order : expiredOrders) {
            UpdateWrapper<Order> uw = new UpdateWrapper<>();
            uw.eq("ord_id", order.getOrderId());
            uw.set("ord_status", 3);
            uw.set("ord_cancel_time", now);
            uw.set("ord_cancel_reason", "超时未支付，系统自动取消");
            uw.set("ord_pay_deadline", null);
            uw.set("order_amount", null);
            uw.set("ord_pay_method", null);
            uw.set("ord_update_time", now);
            orderMapper.update(null, uw);

            Schedule schedule = JSON.parseObject(
                    JSON.toJSONString(scheduleFeign.getSchedule(order.getOrderScheduleId()).getData()),
                    Schedule.class);
            if (schedule != null) {
                int seatCount = order.getOrderSeat() != null
                    ? order.getOrderSeat().split(",").length
                    : 1;
                scheduleFeign.restore(order.getOrderScheduleId(), seatCount);
            }
        }
    }
}
