package org.example.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单实体类
 */
@TableName("ord")
@ApiModel("订单实体")
public class Order {
    @TableId(value = "ord_id", type = IdType.AUTO)
    @ApiModelProperty("订单ID")
    private Integer orderId;

    @TableField("ord_no")
    @ApiModelProperty("订单编号")
    private String orderNo;

    @TableField("ord_schedule_id")
    @ApiModelProperty("排片ID")
    private Integer orderScheduleId;

    @TableField("ord_user_id")
    @ApiModelProperty("用户ID")
    private Integer orderUserId;

    @TableField("order_movie_name")
    @ApiModelProperty("影片名称(快照)")
    private String orderMovieName;

    @TableField("order_cinema_name")
    @ApiModelProperty("影院名称(快照)")
    private String orderCinemaName;

    @TableField("order_hall")
    @ApiModelProperty("影厅号(快照)")
    private Integer orderHall;

    @TableField("ord_seat")
    @ApiModelProperty("座位号")
    private String orderSeat;

    @TableField("ord_status")
    @ApiModelProperty("订单状态 0=待支付 1=已支付 2=已完成 3=已取消 4=已退款")
    private Integer orderStatus;

    @TableField("order_amount")
    @ApiModelProperty("实付金额")
    private Double orderAmount;

    @TableField("ord_pay_time")
    @ApiModelProperty("支付时间")
    private String orderPayTime;

    @TableField("ord_pay_method")
    @ApiModelProperty("支付方式 1=微信 2=支付宝")
    private Integer orderPayMethod;

    @TableField("ord_pay_deadline")
    @ApiModelProperty("支付截止时间")
    private String orderPayDeadline;

    @TableField("ord_cancel_time")
    @ApiModelProperty("取消时间")
    private String orderCancelTime;

    @TableField("ord_cancel_reason")
    @ApiModelProperty("取消原因")
    private String orderCancelReason;

    @TableField("ord_complete_time")
    @ApiModelProperty("完成时间")
    private String orderCompletelTime;

    @TableField("ord_create_time")
    @ApiModelProperty("创建时间")
    private String orderCreateTime;

    @TableField("ord_update_time")
    @ApiModelProperty("更新时间")
    private String orderUpdateTime;

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderScheduleId() {
        return orderScheduleId;
    }

    public void setOrderScheduleId(Integer orderScheduleId) {
        this.orderScheduleId = orderScheduleId;
    }

    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderMovieName() {
        return orderMovieName;
    }

    public void setOrderMovieName(String orderMovieName) {
        this.orderMovieName = orderMovieName;
    }

    public String getOrderCinemaName() {
        return orderCinemaName;
    }

    public void setOrderCinemaName(String orderCinemaName) {
        this.orderCinemaName = orderCinemaName;
    }

    public Integer getOrderHall() {
        return orderHall;
    }

    public void setOrderHall(Integer orderHall) {
        this.orderHall = orderHall;
    }

    public String getOrderSeat() {
        return orderSeat;
    }

    public void setOrderSeat(String orderSeat) {
        this.orderSeat = orderSeat;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Integer getOrderPayMethod() {
        return orderPayMethod;
    }

    public void setOrderPayMethod(Integer orderPayMethod) {
        this.orderPayMethod = orderPayMethod;
    }

    public String getOrderPayDeadline() {
        return orderPayDeadline;
    }

    public void setOrderPayDeadline(String orderPayDeadline) {
        this.orderPayDeadline = orderPayDeadline;
    }

    public String getOrderCancelTime() {
        return orderCancelTime;
    }

    public void setOrderCancelTime(String orderCancelTime) {
        this.orderCancelTime = orderCancelTime;
    }

    public String getOrderCancelReason() {
        return orderCancelReason;
    }

    public void setOrderCancelReason(String orderCancelReason) {
        this.orderCancelReason = orderCancelReason;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(String orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public String getOrderCompletelTime() {
        return orderCompletelTime;
    }

    public void setOrderCompletelTime(String orderCompletelTime) {
        this.orderCompletelTime = orderCompletelTime;
    }

    public Order(Integer orderId, String orderNo, Integer orderScheduleId, Integer orderUserId, String orderMovieName, String orderCinemaName, Integer orderHall, String orderSeat, Integer orderStatus, Double orderAmount, String orderPayTime, Integer orderPayMethod, String orderPayDeadline, String orderCancelTime, String orderCancelReason, String orderCompletelTime, String orderCreateTime, String orderUpdateTime) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderScheduleId = orderScheduleId;
        this.orderUserId = orderUserId;
        this.orderMovieName = orderMovieName;
        this.orderCinemaName = orderCinemaName;
        this.orderHall = orderHall;
        this.orderSeat = orderSeat;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.orderPayTime = orderPayTime;
        this.orderPayMethod = orderPayMethod;
        this.orderPayDeadline = orderPayDeadline;
        this.orderCancelTime = orderCancelTime;
        this.orderCancelReason = orderCancelReason;
        this.orderCompletelTime = orderCompletelTime;
        this.orderCreateTime = orderCreateTime;
        this.orderUpdateTime = orderUpdateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", orderScheduleId=" + orderScheduleId +
                ", orderUserId=" + orderUserId +
                ", orderMovieName='" + orderMovieName + '\'' +
                ", orderCinemaName='" + orderCinemaName + '\'' +
                ", orderHall=" + orderHall +
                ", orderSeat='" + orderSeat + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderAmount=" + orderAmount +
                ", orderPayTime='" + orderPayTime + '\'' +
                ", orderPayMethod=" + orderPayMethod +
                ", orderPayDeadline='" + orderPayDeadline + '\'' +
                ", orderCancelTime='" + orderCancelTime + '\'' +
                ", orderCancelReason='" + orderCancelReason + '\'' +
                ", orderCompletelTime='" + orderCompletelTime + '\'' +
                ", orderCreateTime='" + orderCreateTime + '\'' +
                ", orderUpdateTime='" + orderUpdateTime + '\'' +
                '}';
    }
}
