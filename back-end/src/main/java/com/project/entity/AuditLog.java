package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long logId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    private String action;

    @Column(name = "timestamp")
    private LocalDateTime currentTime;


    public AuditLog(long logId, Order order, String action, LocalDateTime currentTime) {
        this.logId = logId;
        this.order = order;
        this.action = action;
        this.currentTime = currentTime;
    }

    public AuditLog() {
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "logId=" + logId +
                ", order=" + order +
                ", action='" + action + '\'' +
                ", currentTime=" + currentTime +
                '}';
    }
}
