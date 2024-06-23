package com.project.response;

import java.time.LocalDateTime;

public class FailedOrderResponse {

    private long order_Id;
    private String reason;
    private LocalDateTime currentDateAndTime;

    public FailedOrderResponse(long order_Id, String reason, LocalDateTime currentDateAndTime) {
        this.order_Id = order_Id;
        this.reason = reason;
        this.currentDateAndTime = currentDateAndTime;
    }

    public FailedOrderResponse() {
    }

    public long getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(long order_Id) {
        this.order_Id = order_Id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCurrentDateAndTime() {
        return currentDateAndTime;
    }

    public void setCurrentDateAndTime(LocalDateTime currentDateAndTime) {
        this.currentDateAndTime = currentDateAndTime;
    }

    @Override
    public String toString() {
        return "FailedOrderResponse{" +
                "order_Id=" + order_Id +
                ", reason='" + reason + '\'' +
                ", currentDateAndTime=" + currentDateAndTime +
                '}';
    }
}
