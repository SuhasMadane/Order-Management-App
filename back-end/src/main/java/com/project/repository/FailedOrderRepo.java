package com.project.repository;

import com.project.entity.FailedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FailedOrderRepo extends JpaRepository<FailedOrder,Long> {

    @Query("Select o from FailedOrder o")
    public List<FailedOrder> getAllFailedOrders();
}
