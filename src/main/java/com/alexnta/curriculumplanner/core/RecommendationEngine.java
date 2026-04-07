package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.EligibilityResultDTO;
import com.alexnta.curriculumplanner.dto.MissingRequirementDTO;
import com.alexnta.curriculumplanner.dto.RecommendedSubjectDTO;
import com.alexnta.curriculumplanner.model.Subject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class RecommendationEngine {

    private final EligibilityEngine eligibilityEngine;

    public RecommendationEngine() {
        this(new EligibilityEngine());
    }

    public RecommendationEngine(EligibilityEngine eligibilityEngine) {
        this.eligibilityEngine = eligibilityEngine;
    }

    public List<RecommendedSubjectDTO> getRecommendedSubjects(List<Subject> allSubjects, Set<String> passedSubjectCodes) {
        List<RecommendedSubjectDTO> recommendedSubjects = new ArrayList<>();

        for (Subject subject : allSubjects) {
            if (passedSubjectCodes.contains(subject.getCode())) {
                continue;
            }

            EligibilityResultDTO result = eligibilityEngine.evaluateSubjectEligibility(
                    subject.getSubjectId(),
                    passedSubjectCodes
            );
            if (result.isEligible()) {
                recommendedSubjects.add(new RecommendedSubjectDTO(
                        subject.getSubjectId(),
                        subject.getCode(),
                        subject.getName(),
                        subject.getTermNo(),
                        subject.isActive()
                ));
            }
        }

        recommendedSubjects.sort(
                Comparator.comparingInt(RecommendedSubjectDTO::getTermNo)
                        .thenComparing(RecommendedSubjectDTO::getSubjectCode)
        );
        return recommendedSubjects;
    }

    public List<BlockedSubjectDTO> getBlockedSubjects(List<Subject> allSubjects, Set<String> passedSubjectCodes) {
        List<BlockedSubjectDTO> blockedSubjects = new ArrayList<>();

        for (Subject subject : allSubjects) {
            if (passedSubjectCodes.contains(subject.getCode())) {
                continue;
            }

            EligibilityResultDTO result = eligibilityEngine.evaluateSubjectEligibility(
                    subject.getSubjectId(),
                    passedSubjectCodes
            );
            if (!result.isEligible()) {
                BlockedSubjectDTO blockedSubject = new BlockedSubjectDTO();
                blockedSubject.setSubjectId(subject.getSubjectId());
                blockedSubject.setSubjectCode(subject.getCode());
                blockedSubject.setSubjectName(subject.getName());
                blockedSubject.setBlockingReasons(toBlockingReasons(result.getMissingRequirements()));
                blockedSubjects.add(blockedSubject);
            }
        }

        blockedSubjects.sort(
                Comparator.comparing(BlockedSubjectDTO::getSubjectCode)
        );
        return blockedSubjects;
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
