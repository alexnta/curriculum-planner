package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.EligibilityResultDTO;
import com.alexnta.curriculumplanner.dto.MissingRequirementDTO;
import com.alexnta.curriculumplanner.dto.PlanRequestDTO;
import com.alexnta.curriculumplanner.dto.PlanValidationResultDTO;
import com.alexnta.curriculumplanner.dto.RecommendedSubjectDTO;
import com.alexnta.curriculumplanner.model.Subject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlanValidator {

    private final EligibilityEngine eligibilityEngine;

    public PlanValidator() {
        this(new EligibilityEngine());
    }

    public PlanValidator(EligibilityEngine eligibilityEngine) {
        this.eligibilityEngine = eligibilityEngine;
    }

    public PlanValidationResultDTO validatePlan(PlanRequestDTO request, List<Subject> allSubjects,
                                                Set<String> passedSubjectCodes) {
        Map<Integer, Subject> subjectsById = new LinkedHashMap<>();
        Map<String, Subject> subjectsByCode = new LinkedHashMap<>();
        for (Subject subject : allSubjects) {
            subjectsById.put(subject.getSubjectId(), subject);
            subjectsByCode.put(subject.getCode(), subject);
        }

        List<Subject> selectedSubjects = resolveSelectedSubjects(request, subjectsById, subjectsByCode);
        if (selectedSubjects.size() > request.getMaxSubjects()) {
            return new PlanValidationResultDTO(
                    false,
                    new ArrayList<>(),
                    rejectAllForMaxSubjectCount(selectedSubjects, request.getMaxSubjects())
            );
        }

        List<RecommendedSubjectDTO> acceptedSelections = new ArrayList<>();
        List<BlockedSubjectDTO> rejectedSelections = new ArrayList<>();

        for (Subject subject : selectedSubjects) {
            if (passedSubjectCodes.contains(subject.getCode())) {
                rejectedSelections.add(new BlockedSubjectDTO(
                        subject.getSubjectId(),
                        subject.getCode(),
                        subject.getName(),
                        List.of("Subject already passed.")
                ));
                continue;
            }

            EligibilityResultDTO eligibilityResult = eligibilityEngine.evaluateSubjectEligibility(
                    subject.getSubjectId(),
                    passedSubjectCodes
            );
            if (eligibilityResult.isEligible()) {
                acceptedSelections.add(new RecommendedSubjectDTO(
                        subject.getSubjectId(),
                        subject.getCode(),
                        subject.getName(),
                        subject.getTermNo(),
                        subject.isActive()
                ));
            } else {
                rejectedSelections.add(new BlockedSubjectDTO(
                        subject.getSubjectId(),
                        subject.getCode(),
                        subject.getName(),
                        toBlockingReasons(eligibilityResult.getMissingRequirements())
                ));
            }
        }

        return new PlanValidationResultDTO(rejectedSelections.isEmpty(), acceptedSelections, rejectedSelections);
    }

    private List<Subject> resolveSelectedSubjects(PlanRequestDTO request, Map<Integer, Subject> subjectsById,
                                                  Map<String, Subject> subjectsByCode) {
        Set<Integer> resolvedSubjectIds = new LinkedHashSet<>();

        for (Integer subjectId : request.getSelectedSubjectIds()) {
            if (subjectsById.containsKey(subjectId)) {
                resolvedSubjectIds.add(subjectId);
            }
        }

        for (String subjectCode : request.getSelectedSubjectCodes()) {
            Subject subject = subjectsByCode.get(subjectCode);
            if (subject != null) {
                resolvedSubjectIds.add(subject.getSubjectId());
            }
        }

        List<Subject> selectedSubjects = new ArrayList<>();
        for (Integer subjectId : resolvedSubjectIds) {
            selectedSubjects.add(subjectsById.get(subjectId));
        }
        return selectedSubjects;
    }

    private List<BlockedSubjectDTO> rejectAllForMaxSubjectCount(List<Subject> selectedSubjects, int maxSubjects) {
        List<BlockedSubjectDTO> rejectedSelections = new ArrayList<>();
        for (Subject subject : selectedSubjects) {
            rejectedSelections.add(new BlockedSubjectDTO(
                    subject.getSubjectId(),
                    subject.getCode(),
                    subject.getName(),
                    List.of("Plan exceeds max subject count of " + maxSubjects + ".")
            ));
        }
        return rejectedSelections;
    }

    private List<String> toBlockingReasons(List<MissingRequirementDTO> missingRequirements) {
        List<String> reasons = new ArrayList<>();
        for (MissingRequirementDTO missingRequirement : missingRequirements) {
            reasons.add("Missing prerequisite: "
                    + missingRequirement.getSubjectCode()
                    + " - "
                    + missingRequirement.getSubjectName());
        }
        return reasons;
    }
}
