package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long order_Id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Catalog book;

    private int quantity;
    private float totalPrice;
    private LocalDate orderDate;

    @JsonIgnore
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuditLog> auditLogs = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "order")
    private FailedOrder failedOrder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    public Order(long order_Id, Catalog book, int quantity, float totalPrice, LocalDate orderDate, List<AuditLog> auditLogs, FailedOrder failedOrder, Student student) {
        this.order_Id = order_Id;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.auditLogs = auditLogs;
        this.failedOrder = failedOrder;
        this.student = student;
    }

    public Order() {
    }

    public long getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(long order_Id) {
        this.order_Id = order_Id;
    }

    public Catalog getBook() {
        return book;
    }

    public void setBook(Catalog book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    public void setAuditLogs(List<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }

    public FailedOrder getFailedOrder() {
        return failedOrder;
    }

    public void setFailedOrder(FailedOrder failedOrder) {
        this.failedOrder = failedOrder;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

//    @Override
//    public String toString() {
//        return "Order{" +
//                "order_Id=" + order_Id +
//                ", book=" + book +
//                ", quantity=" + quantity +
//                ", totalPrice=" + totalPrice +
//                ", orderDate=" + orderDate +
//                ", auditLogs=" + auditLogs +
//                ", failedOrder=" + failedOrder +
//                ", student=" + student +
//                '}';
//    }
}
