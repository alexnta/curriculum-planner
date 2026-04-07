package com.alexnta.curriculumplanner.dto;

import java.util.ArrayList;
import java.util.List;

public class PlanRequestDTO {

    private int studentId;
    private List<Integer> selectedSubjectIds = new ArrayList<>();
    private List<String> selectedSubjectCodes = new ArrayList<>();
    private int maxSubjects;

    public PlanRequestDTO() {
    }

    public PlanRequestDTO(int studentId, List<Integer> selectedSubjectIds, List<String> selectedSubjectCodes,
                          int maxSubjects) {
        this.studentId = studentId;
        this.selectedSubjectIds = selectedSubjectIds;
        this.selectedSubjectCodes = selectedSubjectCodes;
        this.maxSubjects = maxSubjects;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<Integer> getSelectedSubjectIds() {
        return selectedSubjectIds;
    }

    public void setSelectedSubjectIds(List<Integer> selectedSubjectIds) {
        this.selectedSubjectIds = selectedSubjectIds;
    }

    public List<String> getSelectedSubjectCodes() {
        return selectedSubjectCodes;
    }

    public void setSelectedSubjectCodes(List<String> selectedSubjectCodes) {
        this.selectedSubjectCodes = selectedSubjectCodes;
    }

    public int getMaxSubjects() {
        return maxSubjects;
    }

    public void setMaxSubjects(int maxSubjects) {
        this.maxSubjects = maxSubjects;
    }
}
