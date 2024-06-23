package com.project.repository;

import com.project.entity.AuditLog;
import com.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuditLogRepo extends JpaRepository<AuditLog,Long> {

    @Query("Select a from AuditLog a where a.order=:order")
    public List<AuditLog> findAuditLogsByOrder(Order order);
}
