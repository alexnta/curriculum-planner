package com.alexnta.curriculumplanner.dto;

import java.util.ArrayList;
import java.util.List;

public class EligibilityResultDTO {

    private int targetSubjectId;
    private String targetSubjectCode;
    private String targetSubjectName;
    private boolean eligible;
    private List<MissingRequirementDTO> missingRequirements = new ArrayList<>();

    public EligibilityResultDTO() {
    }

    public EligibilityResultDTO(int targetSubjectId, String targetSubjectCode, String targetSubjectName,
                                boolean eligible, List<MissingRequirementDTO> missingRequirements) {
        this.targetSubjectId = targetSubjectId;
        this.targetSubjectCode = targetSubjectCode;
        this.targetSubjectName = targetSubjectName;
        this.eligible = eligible;
        this.missingRequirements = missingRequirements;
    }

    public int getTargetSubjectId() {
        return targetSubjectId;
    }

    public void setTargetSubjectId(int targetSubjectId) {
        this.targetSubjectId = targetSubjectId;
    }

    public String getTargetSubjectCode() {
        return targetSubjectCode;
    }

    public void setTargetSubjectCode(String targetSubjectCode) {
        this.targetSubjectCode = targetSubjectCode;
    }

    public String getTargetSubjectName() {
        return targetSubjectName;
    }

    public void setTargetSubjectName(String targetSubjectName) {
        this.targetSubjectName = targetSubjectName;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public List<MissingRequirementDTO> getMissingRequirements() {
        return missingRequirements;
    }

    public void setMissingRequirements(List<MissingRequirementDTO> missingRequirements) {
        this.missingRequirements = missingRequirements;
    }
}
