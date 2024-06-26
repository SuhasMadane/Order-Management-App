package com.project.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SignInRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "It must be a valid email")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
