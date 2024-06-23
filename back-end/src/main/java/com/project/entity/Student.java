package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long student_Id;

    private String firstName;
    private String lastName;
    private String emailId;
    private String passWord;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Student(long student_Id, String firstName, String lastName, String emailId, String passWord, List<Order> orders) {
        this.student_Id = student_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.passWord = passWord;
        this.orders = orders;
    }

    public Student() {
    }

    public long getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(long student_Id) {
        this.student_Id = student_Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_Id=" + student_Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", orders=" + orders +
                '}';
    }
}
