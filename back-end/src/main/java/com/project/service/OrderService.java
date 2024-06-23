package com.project.service;

import com.project.entity.Order;
import com.project.entity.Student;
import com.project.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void createOrder(Order order) {
        orderRepo.save(order);
    }
    public Order findOrderByOrderId(long order_Id){
        return orderRepo.findOrderByOrderId(order_Id);
    }
    public void updateOrder(Order order){
        orderRepo.saveAndFlush(order);
    }
    public void deleteOrder(Order order){ orderRepo.delete(order);}

    public List<Order> getAllOrdersOfStudent(Student student){
        return orderRepo.getAllOrdersByStudent(student);
    }

    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
}
