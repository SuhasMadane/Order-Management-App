package com.project.controller;

import com.project.entity.Student;
import com.project.exception.StudentException;
import com.project.logging.CustomLog;
import com.project.request.SignInRequest;
import com.project.request.StudentRequest;
import com.project.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private Logger logger= LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    @Autowired
    private CustomLog customLog;

    @PostMapping("/signup")
    public ResponseEntity<String> signupStudent(@Valid  @RequestBody StudentRequest student){
      String methodName = "StudentController.signupStudent";
        try{
        Optional<Student> existingStudent = Optional.ofNullable(studentService.findStudentByEmail(student.getEmailId()));
        if(existingStudent.isPresent())
            throw new StudentException("Email Already Used");
            Student newStudent = new Student();
            newStudent.setEmailId(student.getEmailId());
            newStudent.setFirstName(student.getFirstName());
            newStudent.setLastName(student.getLastName());
            newStudent.setPassWord(student.getPassWord());
            studentService.createStudent(newStudent);
            //logger.info("Student Created");
            customLog.generateLog(newStudent.getEmailId(),methodName,"Inside Method",null,"Student Created");

            return ResponseEntity.status(HttpStatus.CREATED).body("Sign Up Successful");
        }
        catch(StudentException s){
            //logger.info("Student creation failed");
            customLog.generateLog(null,methodName,"Exception Occurred",s.getMessage(),"Student creation failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: "+s.getMessage());
        }

    }
    @PostMapping("/signin")
    public ResponseEntity<String> signInStudent(@Valid @RequestBody SignInRequest signInRequest){
        String methodName = "StudentController.signInStudent";
        try {
            if(signInRequest.getEmail().equals("") || signInRequest.getPassword().equals(""))
                throw new StudentException("Invalid Credentials");
            Optional<Student> student = Optional.ofNullable(studentService.findStudentByEmail(signInRequest.getEmail()));
            if(student.isPresent()){
                Student existingStudent = studentService.findStudentByEmail(signInRequest.getEmail());
                if(existingStudent.getPassWord().equals(signInRequest.getPassword())) {
                    logger.info("Sign up successfully");
                    customLog.generateLog(existingStudent.getEmailId(),methodName,"Inside Method",null,"Sign up successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(existingStudent.getFirstName() + " " + existingStudent.getLastName());
                }else
                    throw new StudentException("Invalid Password");
            }
            else{
                throw new StudentException("Invalid Email");
            }
        }
        catch (StudentException s){
            logger.error(s.getMessage());
            customLog.generateLog(null,methodName,"Exception Occurred",s.getMessage(),null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s.getMessage());
        }
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Student>> getStudentList(){
        List<Student> studentList = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }

}
