package org.example.dto;

import javax.validation.constraints.NotNull;

public class OrderPayDTO {
    @NotNull(message = "id不能为空")
    private Integer orderId;
    @NotNull(message = "支付方式不能为空")
    private Integer payMethod;
    @NotNull(message = "票价不能为空")
    private Integer payAmount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }
}
