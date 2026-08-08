package org.example.Task;

import org.example.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderTask {

    @Autowired
    private OrderService orderService;

    //时间到了15分钟自动将状态码该为3
    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelExpiredOrders() {
        orderService.cancelExpired();
    }
}
