package com.alexnta.curriculumplanner.model;

public class Subject {

    private int subjectId;
    private String code;
    private String name;
    private int termNo;
    private boolean active;

    public Subject() {
    }

    public Subject(int subjectId, String code, String name, int termNo, boolean active) {
        this.subjectId = subjectId;
        this.code = code;
        this.name = name;
        this.termNo = termNo;
        this.active = active;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTermNo() {
        return termNo;
    }

    public void setTermNo(int termNo) {
        this.termNo = termNo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
