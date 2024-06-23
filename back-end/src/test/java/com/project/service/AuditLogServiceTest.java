package com.project.service;

import com.project.entity.AuditLog;
import com.project.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuditLogServiceTest {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private OrderService orderService;

    @Test
    void getAuditLogsByOrder() {
        int id = 14;
        Order order = orderService.findOrderByOrderId(id);
        List<AuditLog> logs = order.getAuditLogs();
        boolean flag = true;
        for (AuditLog auditLog:logs
             ) {
            if(auditLog.getOrder().getOrder_Id() == id)
                flag = true;
            else {
                flag = false;
                break;
            }
        }
        Assertions.assertEquals(true,flag);
    }
}