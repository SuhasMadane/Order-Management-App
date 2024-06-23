package com.project.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;


public class StudentRequest {

    @NotEmpty(message = "First Name cannot be empty")
    @Size(min = 2,max = 25,message = "First Name must contain minimum 2 characters and maximum 25 characters")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be empty")
    @Size(min = 2,max = 25,message = "Last Name must contain minimum 2 characters and maximum 25 characters")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "It must be a valid email")
    private String emailId;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String passWord;


    public StudentRequest( String firstName, String lastName, String emailId, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.passWord = passWord;
    }

    public StudentRequest() {
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

    @Override
    public String toString() {
        return "Student{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
