package com.alexnta.curriculumplanner.model;

public class StudentResult {

    private int studentSubjectResultId;
    private int studentId;
    private int subjectId;
    private ResultStatus status;
    private String completedTerm;

    public StudentResult() {
    }

    public StudentResult(int studentSubjectResultId, int studentId, int subjectId,
                         ResultStatus status, String completedTerm) {
        this.studentSubjectResultId = studentSubjectResultId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.status = status;
        this.completedTerm = completedTerm;
    }

    public int getStudentSubjectResultId() {
        return studentSubjectResultId;
    }

    public void setStudentSubjectResultId(int studentSubjectResultId) {
        this.studentSubjectResultId = studentSubjectResultId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getCompletedTerm() {
        return completedTerm;
    }

    public void setCompletedTerm(String completedTerm) {
        this.completedTerm = completedTerm;
    }
}
