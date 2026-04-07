package com.alexnta.curriculumplanner.dto;

import java.util.ArrayList;
import java.util.List;

public class BlockedSubjectDTO {

    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private List<String> blockingReasons = new ArrayList<>();

    public BlockedSubjectDTO() {
    }

    public BlockedSubjectDTO(int subjectId, String subjectCode, String subjectName, List<String> blockingReasons) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.blockingReasons = blockingReasons;
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

    public List<String> getBlockingReasons() {
        return blockingReasons;
    }

    public void setBlockingReasons(List<String> blockingReasons) {
        this.blockingReasons = blockingReasons;
    }
}
