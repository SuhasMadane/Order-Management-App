package com.project.request;

import jakarta.validation.constraints.*;

public class OrderRequest {

    @NotEmpty(message = "Book Title cannot be empty")
    @Size(min = 2,max = 25,message = "Book Title must contain minimum 2 characters and maximum 25 characters")
    private String bookTitle;

    @NotNull
    @Min(value = 1,message = "Book Quantity must be greater than 0")
    private int bookQuantity;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "It must be a valid email")
    private String studentEmail;

    public OrderRequest(String bookName, int bookQuantity, String studentEmail) {
        this.bookTitle = bookName;
        this.bookQuantity = bookQuantity;
        this.studentEmail = studentEmail;
    }

    public OrderRequest() {
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }


    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "bookName='" + bookTitle + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", studentEmail='" + studentEmail + '\'' +
                '}';
    }
}

