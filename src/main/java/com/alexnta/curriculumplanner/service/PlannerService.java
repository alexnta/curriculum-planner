package com.alexnta.curriculumplanner.service;

import com.alexnta.curriculumplanner.core.PlanValidator;
import com.alexnta.curriculumplanner.core.RecommendationEngine;
import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.PlanRequestDTO;
import com.alexnta.curriculumplanner.dto.PlanValidationResultDTO;
import com.alexnta.curriculumplanner.dto.RecommendedSubjectDTO;
import com.alexnta.curriculumplanner.model.Subject;

import java.util.List;
import java.util.Set;

public class PlannerService {

    private final SubjectService subjectService;
    private final StudentProgressService studentProgressService;
    private final RecommendationEngine recommendationEngine;
    private final PlanValidator planValidator;

    public PlannerService() {
        this(new SubjectService(), new StudentProgressService(), new RecommendationEngine(), new PlanValidator());
    }

    public PlannerService(SubjectService subjectService, StudentProgressService studentProgressService,
                          RecommendationEngine recommendationEngine, PlanValidator planValidator) {
        this.subjectService = subjectService;
        this.studentProgressService = studentProgressService;
        this.recommendationEngine = recommendationEngine;
        this.planValidator = planValidator;
    }

    public List<RecommendedSubjectDTO> getRecommendedSubjects(int studentId) {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        Set<String> passedSubjectCodes = studentProgressService.getPassedSubjectCodes(studentId);
        return recommendationEngine.getRecommendedSubjects(allSubjects, passedSubjectCodes);
    }

    public List<BlockedSubjectDTO> getBlockedSubjects(int studentId) {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        Set<String> passedSubjectCodes = studentProgressService.getPassedSubjectCodes(studentId);
        return recommendationEngine.getBlockedSubjects(allSubjects, passedSubjectCodes);
    }

    public PlanValidationResultDTO validatePlan(PlanRequestDTO request) {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        Set<String> passedSubjectCodes = studentProgressService.getPassedSubjectCodes(request.getStudentId());
        return planValidator.validatePlan(request, allSubjects, passedSubjectCodes);
    }
}
