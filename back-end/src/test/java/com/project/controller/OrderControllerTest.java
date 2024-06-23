package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.AuditLog;
import com.project.entity.Catalog;
import com.project.entity.Order;
import com.project.entity.Student;
import com.project.logging.CustomLog;
import com.project.request.OrderRequest;
import com.project.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomLog customLog;
    @MockBean
    private CatalogService catalogService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AuditLogService auditLogService;
    @MockBean
    private FailedOrderService failedOrderService;

    @Test
    void createOrder() throws Exception{
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setBookTitle("12 Rules Of Life");
        orderRequest.setStudentEmail("tejas@gmail.com");
        orderRequest.setBookQuantity(2);

        Catalog book = new Catalog();
        book.setTitle(orderRequest.getBookTitle());
        book.setAuthor("New Author");
        book.setPrice(139);
        book.setQuantity(6);
        List<Order> orders = new ArrayList<>();
        book.setOrders(orders);

        Student s = new Student();
        s.setFirstName("Tejas");
        s.setLastName("Karande");
        s.setPassWord("Tejas@123");
        s.setEmailId("tejas@gmail.com");
        List<Order> thisStudentOrders = new ArrayList<>();
        s.setOrders(thisStudentOrders);

        Mockito.when(catalogService.findBookByTitle("12 Rules Of Life")).thenReturn(book);
        Mockito.when(studentService.findStudentByEmail("tejas@gmail.com")).thenReturn(s);

        mockMvc.perform(MockMvcRequestBuilders
                       .post("/orders/create")
                .content(new ObjectMapper().writeValueAsString(orderRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Order Created Succesfully"));
    }

//    @Test
//    void placeOrder() throws Exception{
//        int orderId = 57;
//        Catalog book = new Catalog();
//        book.setTitle("Java");
//        book.setQuantity(5);
//        book.setAuthor("James Goslin");
//        book.setPrice(599);
//        Student student = new Student();
//        student.setFirstName("Suhas");
//        student.setLastName("Madane");
//        student.setPassWord("Suhas@123");
//        student.setEmailId("suhasmadane81@gmail.com");
//        Order order = new Order();
//        order.setBook(book);
//        order.setStudent(student);
//        order.setQuantity(2);
//        order.setTotalPrice(order.getQuantity()*book.getPrice());
//        List<Order> orders = new ArrayList<>();
//        orders.add(order);
//        student.setOrders(orders);
//        order.setStudent(student);
//        List<AuditLog> logs = Arrays.asList(new AuditLog(),new AuditLog());
//        order.setAuditLogs(logs);
//
//        Mockito.when(catalogService.findBookByTitle("Java")).thenReturn(book);
//        //Mockito.when(studentService.)
//        Mockito.when(orderService.findOrderByOrderId(orderId)).thenReturn(order);
//
//           mockMvc.perform(MockMvcRequestBuilders.get("/orders/place/{order_Id}",orderId)
//                           .contentType(MediaType.APPLICATION_JSON)
//                           .accept(MediaType.APPLICATION_JSON))
//                   .andExpect(status().isCreated())
//                   .andExpect(content().string("Order Placed Successfully"));
//    }
}