package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "failed_orders")
public class FailedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failed_order_id")
    private long faildOrderId;

    private String reason;

    @Column(name = "timestamp")
    private LocalDateTime currentTime;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public FailedOrder(long faildOrderId, String reason, LocalDateTime currentTime, Order order) {
        this.faildOrderId = faildOrderId;
        this.reason = reason;
        this.currentTime = currentTime;
        this.order = order;
    }

    public FailedOrder() {
    }

    public long getFaildOrderId() {
        return faildOrderId;
    }

    public void setFaildOrderId(long faildOrderId) {
        this.faildOrderId = faildOrderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "FailedOrder{" +
                "faildOrderId=" + faildOrderId +
                ", reason='" + reason + '\'' +
                ", currentTime=" + currentTime +
                ", order=" + order +
                '}';
    }
}
