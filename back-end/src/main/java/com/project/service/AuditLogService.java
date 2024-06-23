package com.project.service;

import com.project.entity.AuditLog;
import com.project.entity.Order;
import com.project.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepo auditLogRepo;

    public void createAuditLog(AuditLog auditLog){
        auditLogRepo.save(auditLog);
    }


    public List<AuditLog> getAuditLogsByOrder(Order order){
        return auditLogRepo.findAuditLogsByOrder(order);
    }

}
