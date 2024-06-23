package com.project.controller;

import com.project.entity.Student;
import com.project.logging.CustomLog;
import com.project.request.SignInRequest;
import com.project.request.StudentRequest;
import com.project.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @MockBean
    private CustomLog customLog;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void signupStudent() throws Exception{
        StudentRequest stdrq = new StudentRequest();
        stdrq.setFirstName("First");
        stdrq.setLastName("Last");
        stdrq.setEmailId("first@gmail.com");
        stdrq.setPassWord("First@123");
        
        mockMvc.perform(MockMvcRequestBuilders
                 .post("/student/signup")
                 .content(new ObjectMapper().writeValueAsString(stdrq))
                        .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                 .andExpect(content().string("Sign Up Successful"));
    }

    @Test
    void signInStudent() throws Exception{

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("suhasmadane81@gmail.com");
        signInRequest.setPassword("Suhas@123");

        Mockito.when(studentService.findStudentByEmail("suhasmadane81@gmail.com")).thenReturn(new Student(1,"Suhas","Madane","suhasmadane81@gmail.com","Suhas@123",null));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student/signin")
                .content(new ObjectMapper().writeValueAsString(signInRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Suhas Madane"));
    }

    @Test
    void getStudentList() throws Exception{

        List<Student> studentList = Arrays.asList(new Student(),new Student());

        Mockito.when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                       .get("/student/getAllStudents")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$",hasSize(2)));
    }
}