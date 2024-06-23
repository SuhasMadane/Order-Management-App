package com.project.service;

import com.project.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void createStudent() {
        Student std = new Student();
        std.setFirstName("Akash");
        std.setLastName("Mahanavar");
        std.setEmailId("akash@gmail.com");
        std.setPassWord("Akash@123");
        studentService.createStudent(std);


        Student student = studentService.findStudentByEmail(std.getEmailId());
        Assertions.assertEquals(std.getEmailId(),student.getEmailId());

    }

}