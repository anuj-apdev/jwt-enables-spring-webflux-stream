package com.anu.apdev.springboot.weaveflux.model;


import java.util.Date;

public class StudentMarks {

    private Student student;
    private Date date;

    public StudentMarks(Student student, Date date) {
        this.student = student;
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}