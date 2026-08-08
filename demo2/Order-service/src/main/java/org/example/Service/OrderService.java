package org.example.Service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    List<Order> findByUserId(Integer userId);

    IPage<Order> findByPage(Integer page, Order order);

    void add(Order order);

    Order findById(Integer id);

    void update(Order order);

    void delete(Integer id);

    Integer count();

    void pay(Integer orderId, Integer payMethod,Integer payamount);

    void complete(Integer orderId);

    void cancel(Integer orderId, String reason);

    void refund(Integer orderId);

    void cancelExpired();
}
