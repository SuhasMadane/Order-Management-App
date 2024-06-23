package com.project.service;

import com.project.entity.Student;
import com.project.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public void createStudent(Student student){
        studentRepo.save(student);
    }

    public Student findStudentByStudentId(long id){
        return studentRepo.findStudentByStudent_Id(id);
    }

    public Student findStudentByEmail(String email){
        return studentRepo.findStudentByEmailId(email);
    }

    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }
}
