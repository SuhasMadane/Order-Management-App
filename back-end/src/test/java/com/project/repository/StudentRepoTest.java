package com.project.repository;

import com.project.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@Testcontainers
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    @ActiveProfiles("test")
class StudentRepoTest {

//    @Container
//    MySQLContainer mySQLContainer = new MySQLContainer<>("mysql-database-for-test")
//            .withDatabaseName("test-db").withUsername("thisUser").withPassword("thisUser");

    @Autowired
    private StudentRepo studentRepo;

    @Test
    void shouldSaveStudent(){
        Student newStudent = new Student();
        newStudent.setFirstName("New");
        newStudent.setLastName("Student");
        newStudent.setEmailId("new@gmail.com");
        newStudent.setPassWord("New@123");
        Student savedStudent = studentRepo.save(newStudent);
        Assertions.assertThat(savedStudent).usingRecursiveComparison().ignoringFields("student_Id").isEqualTo(newStudent);
    }


    @Test
    @Sql("classpath:test-data2.sql")
    void findStudentByEmailId() {
        Student student = studentRepo.findStudentByEmailId("first@gmail.com");
        Assertions.assertThat(student.getFirstName()).isEqualTo("first");
    }
}