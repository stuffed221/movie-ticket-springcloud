package org.example.vo;

public class OrderVO {
    private Integer orderId;
    private String orderNo;
    private Integer orderScheduleId;
    private Integer orderUserId;
    private String orderMovieName;
    private String orderCinemaName;
    private Integer orderHall;
    private String orderSeat;
    private Integer orderStatus;
    private Double orderAmount;
    private String orderPayTime;
    private Integer orderPayMethod;
    private String orderPayDeadline;
    private String orderCancelTime;
    private String orderCancelReason;
    private String orderCreateTime;
    private String userName;

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Integer getOrderScheduleId() { return orderScheduleId; }
    public void setOrderScheduleId(Integer orderScheduleId) { this.orderScheduleId = orderScheduleId; }

    public Integer getOrderUserId() { return orderUserId; }
    public void setOrderUserId(Integer orderUserId) { this.orderUserId = orderUserId; }

    public String getOrderMovieName() { return orderMovieName; }
    public void setOrderMovieName(String orderMovieName) { this.orderMovieName = orderMovieName; }

    public String getOrderCinemaName() { return orderCinemaName; }
    public void setOrderCinemaName(String orderCinemaName) { this.orderCinemaName = orderCinemaName; }

    public Integer getOrderHall() { return orderHall; }
    public void setOrderHall(Integer orderHall) { this.orderHall = orderHall; }

    public String getOrderSeat() { return orderSeat; }
    public void setOrderSeat(String orderSeat) { this.orderSeat = orderSeat; }

    public Integer getOrderStatus() { return orderStatus; }
    public void setOrderStatus(Integer orderStatus) { this.orderStatus = orderStatus; }

    public Double getOrderAmount() { return orderAmount; }
    public void setOrderAmount(Double orderAmount) { this.orderAmount = orderAmount; }

    public String getOrderPayTime() { return orderPayTime; }
    public void setOrderPayTime(String orderPayTime) { this.orderPayTime = orderPayTime; }

    public Integer getOrderPayMethod() { return orderPayMethod; }
    public void setOrderPayMethod(Integer orderPayMethod) { this.orderPayMethod = orderPayMethod; }

    public String getOrderPayDeadline() { return orderPayDeadline; }
    public void setOrderPayDeadline(String orderPayDeadline) { this.orderPayDeadline = orderPayDeadline; }

    public String getOrderCancelTime() { return orderCancelTime; }
    public void setOrderCancelTime(String orderCancelTime) { this.orderCancelTime = orderCancelTime; }

    public String getOrderCancelReason() { return orderCancelReason; }
    public void setOrderCancelReason(String orderCancelReason) { this.orderCancelReason = orderCancelReason; }

    public String getOrderCreateTime() { return orderCreateTime; }
    public void setOrderCreateTime(String orderCreateTime) { this.orderCreateTime = orderCreateTime; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
