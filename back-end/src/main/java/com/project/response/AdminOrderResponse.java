package com.project.response;

import java.time.LocalDate;

public class AdminOrderResponse {
//    "order_Id": 2,
//            "quantity": 1,
//            "totalPrice": 700.0,
//            "orderDate": "2024-02-23"

    private long order_Id;
    private String bookTitle;
    private int quantity;
    private float totalPrice;
    private LocalDate createdOn;
    private String currentStatus;
    private String email;


    public AdminOrderResponse(long order_Id, String bookTitle, int quantity, float totalPrice, LocalDate createdOn,String currentStatus,String email) {
        this.order_Id = order_Id;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdOn = createdOn;
        this.currentStatus = currentStatus;
        this.email = email;
    }

    public AdminOrderResponse() {
    }

    public long getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(long order_Id) {
        this.order_Id = order_Id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AdminOrderResponse{" +
                "order_Id=" + order_Id +
                ", bookTitle='" + bookTitle + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", createdOn=" + createdOn +
                ", currentStatus='" + currentStatus + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

