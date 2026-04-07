package com.alexnta.curriculumplanner.dto;

import java.util.ArrayList;
import java.util.List;

public class PlanValidationResultDTO {

    private boolean valid;
    private List<RecommendedSubjectDTO> acceptedSelections = new ArrayList<>();
    private List<BlockedSubjectDTO> rejectedSelections = new ArrayList<>();

    public PlanValidationResultDTO() {
    }

    public PlanValidationResultDTO(boolean valid, List<RecommendedSubjectDTO> acceptedSelections,
                                   List<BlockedSubjectDTO> rejectedSelections) {
        this.valid = valid;
        this.acceptedSelections = acceptedSelections;
        this.rejectedSelections = rejectedSelections;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<RecommendedSubjectDTO> getAcceptedSelections() {
        return acceptedSelections;
    }

    public void setAcceptedSelections(List<RecommendedSubjectDTO> acceptedSelections) {
        this.acceptedSelections = acceptedSelections;
    }

    public List<BlockedSubjectDTO> getRejectedSelections() {
        return rejectedSelections;
    }

    public void setRejectedSelections(List<BlockedSubjectDTO> rejectedSelections) {
        this.rejectedSelections = rejectedSelections;
    }
}
