package com.project.logging;

import com.project.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

@Component
public class CustomLog {

    private Logger logger= LoggerFactory.getLogger(CustomLog.class);
    private String studentEmail;
    private String methodName;
    private String action;
    private String error;
    private String remark;
    private String createdAt;

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remarks) {
        this.remark = remarks;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CustomLog{" +
                "studentEmail='" + studentEmail + '\'' +
                ", methodName='" + methodName + '\'' +
                ", action='" + action + '\'' +
                ", error='" + error + '\'' +
                ", remark='" + remark + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public void generateLog(String studentEmail, String methodName, String action, String error, String remark){
        CustomLog newCustomLog = new CustomLog();
        newCustomLog.setStudentEmail(studentEmail);
        newCustomLog.setMethodName(methodName);
        newCustomLog.setAction(action);
        newCustomLog.setError(error);
        newCustomLog.setRemark(remark);
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        newCustomLog.setCreatedAt(LocalDateTime.now().format(formatter));
        if(newCustomLog.error != ""){
            logger.info("Current User Email: "+newCustomLog.getStudentEmail() +", Current Method: "+newCustomLog.getMethodName()+", Current Action: "+newCustomLog.getAction()+", Remark: "+newCustomLog.getRemark()+", Date & Time: "+newCustomLog.getCreatedAt());
        }
        else {
            logger.error("Current User Email: "+newCustomLog.getStudentEmail() +", Current Method: "+newCustomLog.getMethodName()+", Error: "+newCustomLog.getError()+", Current Action: "+newCustomLog.getAction()+", Remark: "+newCustomLog.getRemark()+", Date & Time: "+newCustomLog.getCreatedAt());
        }
    }
}
