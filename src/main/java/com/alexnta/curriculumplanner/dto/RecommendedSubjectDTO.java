package com.alexnta.curriculumplanner.dto;

public class RecommendedSubjectDTO {

    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private int termNo;
    private boolean active;

    public RecommendedSubjectDTO() {
    }

    public RecommendedSubjectDTO(int subjectId, String subjectCode, String subjectName, int termNo, boolean active) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.termNo = termNo;
        this.active = active;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
