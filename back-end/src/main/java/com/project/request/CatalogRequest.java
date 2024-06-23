package com.project.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CatalogRequest {


    @NotEmpty(message = "Book Title cannot be empty")
    @Size(min = 2,max = 25,message = "Book Title must contain minimum 2 characters and maximum 25 characters")
    private String title;

    @NotEmpty(message = "Author cannot be empty")
    @Size(min = 2,max = 25,message = "Author must contain minimum 2 characters and maximum 25 characters")
    private String author;

    @NotNull
    @Min(value = 1,message = "Price must be greater than 0")
    private float price;

    @NotNull
    @Min(value = 1,message = "Quantity must be greater than 0")
    private int quantity;

    public CatalogRequest(long book_Id, String title, String author, float price,int quantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public CatalogRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CatalogRequest{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

