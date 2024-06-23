package com.project.service;

import com.project.entity.FailedOrder;
import com.project.repository.FailedOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailedOrderService {

    @Autowired
    private FailedOrderRepo failedOrderRepo;

    public void createFailedOrder(FailedOrder failedOrder){
        failedOrderRepo.save(failedOrder);
    }

    public List<FailedOrder> getAllFailedOrders(){
        return  failedOrderRepo.getAllFailedOrders();
    }
}
