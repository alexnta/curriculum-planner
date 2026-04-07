package com.alexnta.curriculumplanner.service;

import com.alexnta.curriculumplanner.dal.DBContext;
import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.PlanRequestDTO;
import com.alexnta.curriculumplanner.dto.PlanValidationResultDTO;
import com.alexnta.curriculumplanner.dto.RecommendedSubjectDTO;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlannerServiceTest {

    private final DBContext dbContext = new DBContext();
    private final PlannerService plannerService = new PlannerService();

    @Test
    void getRecommendedSubjectsReturnsEligibleSubjectsForDemoStudent() {
        int demoStudentId = findDemoStudentId();

        List<RecommendedSubjectDTO> recommendedSubjects = plannerService.getRecommendedSubjects(demoStudentId);

        assertFalse(recommendedSubjects.isEmpty());
        assertTrue(recommendedSubjects.stream().anyMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));
        assertTrue(recommendedSubjects.stream().noneMatch(subject ->
                "PRF192".equals(subject.getSubjectCode())
                        || "PRO192".equals(subject.getSubjectCode())
                        || "DBI202".equals(subject.getSubjectCode())
                        || "JPD113".equals(subject.getSubjectCode())));
    }

    @Test
    void getBlockedSubjectsReturnsBlockedSubjectsWithReasons() {
        int demoStudentId = findDemoStudentId();

        List<BlockedSubjectDTO> blockedSubjects = plannerService.getBlockedSubjects(demoStudentId);

        assertFalse(blockedSubjects.isEmpty());
        assertTrue(blockedSubjects.stream().anyMatch(subject ->
                "HSF302".equals(subject.getSubjectCode())
                        && subject.getBlockingReasons().stream().anyMatch(reason -> reason.contains("PRJ301"))));
    }

    @Test
    void validatePlanAcceptsValidPlanWhenPrerequisitesAreSatisfied() {
        int demoStudentId = findDemoStudentId();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("PRJ301"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = plannerService.validatePlan(request);

        assertTrue(result.isValid());
        assertTrue(result.getAcceptedSelections().stream().anyMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));
        assertTrue(result.getRejectedSelections().isEmpty());
    }

    @Test
    void validatePlanRejectsInvalidPlanWithClearReasons() {
        int demoStudentId = findDemoStudentId();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("HSF302"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = plannerService.validatePlan(request);

        assertFalse(result.isValid());
        assertTrue(result.getAcceptedSelections().isEmpty());
        assertTrue(result.getRejectedSelections().stream().anyMatch(subject ->
                "HSF302".equals(subject.getSubjectCode())
                        && subject.getBlockingReasons().stream().anyMatch(reason -> reason.contains("PRJ301"))));
    }

    private int findDemoStudentId() {
        String sql = """
                SELECT student_id
                FROM dbo.students
                WHERE student_code = ?
                """;

        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "DEMO001");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("student_id");
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to find demo student id.", ex);
        }
        throw new IllegalStateException("Demo student DEMO001 not found.");
    }
}
