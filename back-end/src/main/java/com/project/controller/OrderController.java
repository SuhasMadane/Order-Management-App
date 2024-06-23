package com.project.controller;

import com.project.logging.CustomLog;
import com.project.response.*;
import com.project.entity.*;
import com.project.exception.CatalogException;
import com.project.exception.OrderException;
import com.project.exception.StudentException;
import com.project.request.OrderRequest;
import com.project.request.UpdateOrderRequest;
import com.project.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FailedOrderService failedOrderService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private CustomLog customLog;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@Valid  @RequestBody OrderRequest orderRequest) {
        String methodName = "OrderController.createOrder";
        try {
            if (orderRequest.getStudentEmail() == "" || orderRequest.getBookTitle() == "" || orderRequest.getBookQuantity() <= 0)
                throw new OrderException("Order Creation failed, Incomplete Data ");
            Optional<Catalog> existingBook = Optional.ofNullable(catalogService.findBookByTitle(orderRequest.getBookTitle()));
            Optional<Student> existingStudent = Optional.ofNullable(studentService.findStudentByEmail(orderRequest.getStudentEmail()));
            if (existingStudent.isEmpty()) {
                logger.error("Inavlid Eamil Id");
                //customLog.generateLog(null,methodName,null,"Inavlid email",In);
                throw new StudentException("Invalid Email Id");

            }
            if (existingBook.isEmpty()){
                logger.error("Book Not Found");
                throw new CatalogException("Book Not Found");
            }
            //logger.info("Book Found");
            customLog.generateLog(existingStudent.get().getEmailId(),methodName,"Inside Method",null, "Book Found");
            Order order = new Order();
            Catalog book = existingBook.get();
            Student student = existingStudent.get();
            //logger.info("Student Retrived");
            customLog.generateLog(existingStudent.get().getEmailId(),methodName,"Inside Method",null, "Student Retrieved");
            order.setBook(book);
            order.setQuantity(orderRequest.getBookQuantity());
            order.setTotalPrice(book.getPrice() * orderRequest.getBookQuantity());
            order.setStudent(student);
            List<AuditLog> auditLogs = order.getAuditLogs();
            AuditLog newAuditLog = new AuditLog();
            newAuditLog.setAction("created");
            customLog.generateLog(existingStudent.get().getEmailId(),methodName,"Inside Method",null, "Status: created");
            newAuditLog.setCurrentTime(LocalDateTime.now());
            newAuditLog.setOrder(order);
            auditLogs.add(newAuditLog);
            order.setAuditLogs(auditLogs);
            customLog.generateLog(existingStudent.get().getEmailId(),methodName,"Inside Method",null, "AuditLog list updated");
            order.setOrderDate(LocalDate.now());
            List<Order> existingOrders = book.getOrders();
            existingOrders.add(order);
            book.setOrders(existingOrders);
            List<Order> orderList = student.getOrders();
            orderList.add(order);
            student.setOrders(orderList);
            orderService.createOrder(order);
            customLog.generateLog(existingStudent.get().getEmailId(),methodName,"Inside Method",null, "Order Created");
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Created Succesfully");
        } catch (OrderException | CatalogException | StudentException s) {
            logger.info("Custom Exception generated");
            customLog.generateLog(null,methodName,"Exception Occurred",s.getMessage(), "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + s.getMessage());
        } catch (Exception e) {
            logger.info("Internal Exception generated");
            customLog.generateLog(null,methodName,"Exception Occurred",e.getMessage(), "");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/place/{order_Id}")
    public ResponseEntity<String> placeOrder(@PathVariable("order_Id") long order_Id) {
        String methodName = "OrderController.placeOrder";
        Order order = orderService.findOrderByOrderId(order_Id);
        try {
            Optional<Catalog> existingBook = Optional.ofNullable(catalogService.findBookByTitle(order.getBook().getTitle()));
            int existingBookQuantity = existingBook.get().getQuantity();
            int orderBookQuantity = order.getQuantity();
            if (existingBookQuantity < orderBookQuantity) {
                logger.error("Failed to place Order");
                throw new OrderException("Failed to place to Order, the requested quantity of books is currently not available");
            }
            Catalog book = existingBook.get();
            book.setQuantity(existingBookQuantity-orderBookQuantity);
            //logger.info("Book Quantity Updated");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Inside Method",null, "Book Quantity Updated");
            AuditLog newAuditLog = new AuditLog();
            newAuditLog.setOrder(order);
            newAuditLog.setAction("placed");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Inside Method",null, "Status: Placed");
            newAuditLog.setCurrentTime(LocalDateTime.now());
            List<AuditLog> existingAuditLogs = order.getAuditLogs();
            existingAuditLogs.add(newAuditLog);
            order.setAuditLogs(existingAuditLogs);
            //logger.info("AuditLogs Updated");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Inside Method",null, "AuditLogs Updated");
            orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
        } catch (OrderException o) {
            FailedOrder newFailedOrder = new FailedOrder();
            newFailedOrder.setOrder(order);
            newFailedOrder.setReason(o.getMessage());
            newFailedOrder.setCurrentTime(LocalDateTime.now());
            AuditLog newAuditLog = new AuditLog();
            newAuditLog.setOrder(order);
            newAuditLog.setAction("failed");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Exception Occurred",o.getMessage(), "Status: Failed");
            newAuditLog.setCurrentTime(LocalDateTime.now());
            List<AuditLog> existingAuditLogs = order.getAuditLogs();
            existingAuditLogs.add(newAuditLog);
            order.setAuditLogs(existingAuditLogs);
            failedOrderService.createFailedOrder(newFailedOrder);
            orderService.createOrder(order);
            //logger.info("Order Failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + o.getMessage());
        } catch (Exception e) {
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Exception Occurred",e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        String methodName = "OrderController.updateOrder";
        Optional<Order> existingOrder = Optional.ofNullable(orderService.findOrderByOrderId(updateOrderRequest.getExistingOrderId()));
        try {
            if (existingOrder.isEmpty())
                throw new OrderException("Failed to update order,order Not Found");
            Order order = existingOrder.get();
            if (!updateOrderRequest.getStudentEmail().equals(order.getStudent().getEmailId()))
                throw new OrderException("Failed to update order,Invalid Student");
            Catalog book = catalogService.findBookByTitle(updateOrderRequest.getBookTitle());
            order.setBook(book);
            order.setQuantity(updateOrderRequest.getBookQuantity());
            order.setTotalPrice(book.getPrice() * updateOrderRequest.getBookQuantity());
            List<Order> existingOrders = book.getOrders();
            existingOrders.add(order);
            book.setOrders(existingOrders);
            List<AuditLog> auditLogList = order.getAuditLogs();
            AuditLog newAuditLog = new AuditLog();
            newAuditLog.setAction("updated");
            //logger.info("AuditLogs Updated ");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Inside Method",null, "Status: Updated");
            newAuditLog.setCurrentTime(LocalDateTime.now());
            newAuditLog.setOrder(order);
            auditLogList.add(newAuditLog);
            order.setAuditLogs(auditLogList);
            order.setOrderDate(LocalDate.now());
            orderService.createOrder(order);
            //logger.info("Order Updated Successfully");
            customLog.generateLog(order.getStudent().getEmailId(),methodName,"Inside Method",null, "Order Updated Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Updated SuccessFully");
        } catch (OrderException o) {
            customLog.generateLog(null,methodName,"Exception Occurred",o.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + o.getMessage());
        } catch (Exception e) {
            customLog.generateLog(null,methodName,"Exception Occurred",e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<OrderResponse>> getAllOrdersOfStudent(@RequestParam String email) {
        String methodName = "OrderController.getAllOrdersOfStudent";
        Student existingStudent = studentService.findStudentByEmail(email);
        List<Order> ordersOfThisStudent = orderService.getAllOrdersOfStudent(existingStudent);
        if (ordersOfThisStudent.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        List<OrderResponse> userOrders = new ArrayList<>();
        for (Order order : ordersOfThisStudent) {
            OrderResponse newOrderResponse = new OrderResponse();
            newOrderResponse.setOrder_Id(order.getOrder_Id());
            newOrderResponse.setBookTitle(order.getBook().getTitle());
//            logger.info(newOrderResponse.getBookTitle());
            newOrderResponse.setQuantity(order.getQuantity());
            newOrderResponse.setTotalPrice(order.getTotalPrice());
            newOrderResponse.setCreatedOn(order.getOrderDate());
            newOrderResponse.setCurrentStatus(order.getAuditLogs().get(order.getAuditLogs().size()-1).getAction());
            userOrders.add(newOrderResponse);
        }
        logger.info("Loaded orders of current student");
        customLog.generateLog(email,methodName,"Inside Method",null,"Loaded orders of current student" );
        return ResponseEntity.status(HttpStatus.OK).body(userOrders);
    }

    @GetMapping("/getLogs")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsOfOrdersOfStudent(@RequestParam  String email){
        String methodName = "OrderController.getAuditLogsOfOrdersOfStudent";
        Optional<Student> student = Optional.ofNullable(studentService.findStudentByEmail(email));
        try {
            if(student.isPresent()){
                Student existingStudent = student.get();
                List<Order> studentOrders = existingStudent.getOrders();
                //logger.info("Loaded Current Student Orders");
                customLog.generateLog(email,methodName,"Inside Method",null,"Loaded Current Student Orders logs" );
                List<AuditLogResponse> studentAuditLogs = new ArrayList<>();
                for (Order order:studentOrders
                ) {
                    List<AuditLog> currentOrderAuditLogs = order.getAuditLogs();
                    logger.info("Loaded Current Order Logs");
                    customLog.generateLog(email,methodName,"Inside Method",null,"Loaded Current Order Logs" );
                    for (AuditLog auditLog:currentOrderAuditLogs
                         ) {
                        AuditLogResponse newAuditLogResponse = new AuditLogResponse();
                        newAuditLogResponse.setOrder_id(order.getOrder_Id());
                        newAuditLogResponse.setAction(auditLog.getAction());
                        newAuditLogResponse.setLogId(auditLog.getLogId());
                        newAuditLogResponse.setCurrentTime(auditLog.getCurrentTime());
                        studentAuditLogs.add(newAuditLogResponse);
                        logger.info("Updating current order logs");
                        customLog.generateLog(email,methodName,"Inside Method",null,"Updating current order logs" );
                    }

                }
                return ResponseEntity.status(HttpStatus.OK).body(studentAuditLogs);
            }
            else
                throw new OrderException("AuditLogs not found");
        }
        catch (OrderException o){
            customLog.generateLog(email,methodName,"Exception Occurred",o.getMessage(),null );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e){
            customLog.generateLog(email,methodName,"Exception Occurred",e.getMessage(),null );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/getFailedOrders")
    public ResponseEntity<List<FailedOrderResponse>> getAllFailedOrdersOfThisStudent(String email){
        String methodName = "OrderController.getAllFailedOrdersOfThisStudent";
        Optional<Student> student = Optional.ofNullable(studentService.findStudentByEmail(email));
        if(student.isPresent()){
            Student existingStudent = student.get();
            List<Order> studentOrders = existingStudent.getOrders();
            List<FailedOrderResponse> failedOrderList = new ArrayList<>();
            for (Order order:studentOrders
                 ) {
                Optional<FailedOrder> failedOrder = Optional.ofNullable(order.getFailedOrder());
                if(failedOrder.isPresent()) {
                    FailedOrder existingFailedOrder = failedOrder.get();
                    FailedOrderResponse newFailedOrderResponse = new FailedOrderResponse();
                    newFailedOrderResponse.setOrder_Id(existingFailedOrder.getOrder().getOrder_Id());
                    newFailedOrderResponse.setReason(existingFailedOrder.getReason());
                    newFailedOrderResponse.setCurrentDateAndTime(existingFailedOrder.getCurrentTime());
                    //logger.info("Generated new Failed order response");
                    customLog.generateLog(email,methodName,"Inside Method",null,"Generated new Failed order response" );
                    failedOrderList.add(newFailedOrderResponse);
                    //logger.info("Updated all failed orders of current student");
                    customLog.generateLog(email,methodName,"Inside Method",null,"Updated all failed orders of current student" );
                }
                else
                    continue;
            }
            return ResponseEntity.status(HttpStatus.OK).body(failedOrderList);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/delete/{order_Id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("order_Id") long order_Id) {
        String methodName = "OrderController.deleteOrder";
        Optional<Order> order = Optional.ofNullable(orderService.findOrderByOrderId(order_Id));
        try {
            if(order.isPresent()){
                Order existingOrder = order.get();
                Student student = existingOrder.getStudent();
                List<Order> orders = student.getOrders();
                orders.remove(existingOrder);
                orderService.deleteOrder(existingOrder);
                student.setOrders(orders);
                studentService.createStudent(student);
                logger.info("Order Deleted");
                customLog.generateLog(existingOrder.getStudent().getEmailId(),methodName,"Inside Method",null,"Order Deleted" );
                return ResponseEntity.status(HttpStatus.OK).body("Order Deleted Successfully");
            }
            throw new OrderException("Order not found");

        }
        catch (Exception e){
            customLog.generateLog(order.get().getStudent().getEmailId(),methodName,"Exception Occurred",e.getMessage(),null );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<AdminOrderResponse>> getAllOrders(){
        List<Order> orderList = orderService.getAllOrders();
        List<AdminOrderResponse> orderResponseList = new ArrayList<>();
        for (Order order:orderList
             ) {
            AdminOrderResponse newOrderResponse = new AdminOrderResponse();
            newOrderResponse.setOrder_Id(order.getOrder_Id());
            newOrderResponse.setQuantity(order.getQuantity());
            newOrderResponse.setCurrentStatus(order.getAuditLogs().get(order.getAuditLogs().size()-1).getAction());
            newOrderResponse.setTotalPrice(order.getTotalPrice());
            newOrderResponse.setBookTitle(order.getBook().getTitle());
            newOrderResponse.setCreatedOn(order.getOrderDate());
            newOrderResponse.setEmail(order.getStudent().getEmailId());
            orderResponseList.add(newOrderResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseList);
    }

    @GetMapping("/getAllLogs")
    public ResponseEntity<List<AdminAuditLogResponse>> getAllLogs(){
        List<Order> orderList = orderService.getAllOrders();
        List<AdminAuditLogResponse> logList = new ArrayList<>();
        for (Order order:orderList
        ) {
            List<AuditLog> auditLogsOfThisOrder = order.getAuditLogs();
            for(AuditLog auditLog: auditLogsOfThisOrder){
                AdminAuditLogResponse newLog = new AdminAuditLogResponse();
                newLog.setOrder_id(auditLog.getOrder().getOrder_Id());
                newLog.setLogId(auditLog.getLogId());
                newLog.setAction(auditLog.getAction());
                newLog.setCurrentTime(auditLog.getCurrentTime());
                newLog.setEmail(auditLog.getOrder().getStudent().getEmailId());
                logList.add(newLog);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(logList);
    }


}