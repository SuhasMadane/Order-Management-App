package com.project.service;

import com.project.entity.Order;
import com.project.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void findOrderByOrderId() {
        int id = 57;
        Order order = orderService.findOrderByOrderId(57);
        Assertions.assertEquals(57,order.getOrder_Id());
    }

    @Test
    void updateOrder() {
        int id = 57;
        Order order = orderService.findOrderByOrderId(57);
        order.setQuantity(2);
        orderService.updateOrder(order);
        Order updatedOrder = orderService.findOrderByOrderId(57);
        Assertions.assertEquals(2,updatedOrder.getQuantity());
    }

    @Test
    void getAllOrdersOfStudent() {
        int id = 57;
        Order order = orderService.findOrderByOrderId(57);
        Student student = order.getStudent();
        List<Order> orderList = orderService.getAllOrdersOfStudent(student);
        boolean flag = true;
        for (Order order1:orderList
             ) {
            if(student.getEmailId().equals(order1.getStudent().getEmailId())){
                flag = true;
            }
            else{
                flag = false;
                break;
            }

        }
        Assertions.assertEquals(true,flag);

    }
}