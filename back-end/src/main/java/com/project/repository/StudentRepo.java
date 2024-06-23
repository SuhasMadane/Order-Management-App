package com.project.repository;

import com.project.entity.Order;
import com.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentRepo extends JpaRepository<Student,Long> {

    @Query("Select s from Student s where s.student_Id = :student_Id")
    public Student findStudentByStudent_Id(long student_Id);

    @Query("Select s from Student s where s.emailId = :email")
    public Student findStudentByEmailId(String email);




}
