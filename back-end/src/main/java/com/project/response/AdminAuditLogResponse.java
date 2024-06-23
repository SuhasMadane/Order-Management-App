package com.project.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdminAuditLogResponse {


    private long logId;
    private long order_id;
    private String action;
    private LocalDateTime currentTime;
    private String email;

    public AdminAuditLogResponse(long logId, long order_id, String action, LocalDateTime currentTime,String email) {
        this.logId = logId;
        this.order_id = order_id;
        this.action = action;
        this.currentTime = currentTime;
        this.email = email;
    }

    public AdminAuditLogResponse() {
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AdminAuditLogResponse{" +
                "logId=" + logId +
                ", order_id=" + order_id +
                ", action='" + action + '\'' +
                ", currentTime=" + currentTime +
                ", email='" + email + '\'' +
                '}';
    }
}

