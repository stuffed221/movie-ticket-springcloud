package org.example.dto;

import javax.validation.constraints.NotNull;

public class OrderCreatDTO {
    @NotNull(message = "订单编号不能为空")
    private String ordNo;
    @NotNull(message = "排片id不能为空")
    private Integer ordScheduleId;
    @NotNull(message = "用户id不能为空")
    private Integer ordUserId;
    @NotNull(message = "座位号不能为空")
    private Integer ordSeat;
    @NotNull(message = "状态码不能为空")
    private Integer ordStatus;

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public Integer getOrdScheduleId() {
        return ordScheduleId;
    }

    public void setOrdScheduleId(Integer ordScheduleId) {
        this.ordScheduleId = ordScheduleId;
    }

    public Integer getOrdUserId() {
        return ordUserId;
    }

    public void setOrdUserId(Integer ordUserId) {
        this.ordUserId = ordUserId;
    }

    public Integer getOrdSeat() {
        return ordSeat;
    }

    public void setOrdSeat(Integer ordSeat) {
        this.ordSeat = ordSeat;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }
}
