package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long book_Id;

    private String title;
    private String author;
    private float price;
    private int quantity;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Order> orders = new ArrayList<>();

    public Catalog(long book_Id, String title, String author, float price, int quantity, List<Order> orders) {
        this.book_Id = book_Id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.orders = orders;
    }

    public Catalog() {
    }

    public long getBook_Id() {
        return book_Id;
    }

    public void setBook_Id(long book_Id) {
        this.book_Id = book_Id;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    @Override
//    public String toString() {
//        return "Catalog{" +
//                "book_Id=" + book_Id +
//                ", title='" + title + '\'' +
//                ", author='" + author + '\'' +
//                ", price=" + price +
//                ", quantity=" + quantity +
//                ", orders=" + orders +
//                '}';
//    }
}
