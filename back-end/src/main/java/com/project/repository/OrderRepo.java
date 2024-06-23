package com.project.repository;

import com.project.entity.Order;
import com.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("Select o from Order o where o.order_Id = :order_Id")
    public Order findOrderByOrderId(long order_Id);

//    @Query("Update table Order o set o.")
//    public void UpdateBookOrQuantityOfOrder(String bookTitle,int quantity);

    @Query("Select o from Order o where o.student = :student ")
    public List<Order> getAllOrdersByStudent(Student student);
}
