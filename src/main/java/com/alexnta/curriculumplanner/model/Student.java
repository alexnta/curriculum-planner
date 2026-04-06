package com.alexnta.curriculumplanner.model;

public class Student {

    private int studentId;
    private String studentCode;
    private String fullName;

    public Student() {
    }

    public Student(int studentId, String studentCode, String fullName) {
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.fullName = fullName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
