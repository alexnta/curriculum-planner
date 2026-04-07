package com.alexnta.curriculumplanner.dto;

public class MissingRequirementDTO {

    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private String groupName;

    public MissingRequirementDTO() {
    }

    public MissingRequirementDTO(int subjectId, String subjectCode, String subjectName, String groupName) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
