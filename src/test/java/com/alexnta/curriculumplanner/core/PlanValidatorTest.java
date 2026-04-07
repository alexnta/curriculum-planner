package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.DBContext;
import com.alexnta.curriculumplanner.dal.StudentResultDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.PlanRequestDTO;
import com.alexnta.curriculumplanner.dto.PlanValidationResultDTO;
import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanValidatorTest {

    private final DBContext dbContext = new DBContext();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final StudentResultDAO studentResultDAO = new StudentResultDAO();
    private final PlanValidator planValidator = new PlanValidator(new EligibilityEngine());

    @Test
    void rejectsAlreadyPassedSubjects() {
        int demoStudentId = findDemoStudentId();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);
        List<Subject> allSubjects = subjectDAO.findAll();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("PRO192"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = planValidator.validatePlan(request, allSubjects, passedSubjectCodes);

        assertFalse(result.isValid());
        assertTrue(result.getAcceptedSelections().isEmpty());
        assertTrue(result.getRejectedSelections().stream().anyMatch(subject ->
                "PRO192".equals(subject.getSubjectCode())
                        && subject.getBlockingReasons().contains("Subject already passed.")));
    }

    @Test
    void rejectsPlansOverMaxSubjectCount() {
        int demoStudentId = findDemoStudentId();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);
        List<Subject> allSubjects = subjectDAO.findAll();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("PRJ301", "JPD123"));
        request.setMaxSubjects(1);

        PlanValidationResultDTO result = planValidator.validatePlan(request, allSubjects, passedSubjectCodes);

        assertFalse(result.isValid());
        assertTrue(result.getAcceptedSelections().isEmpty());
        assertTrue(result.getRejectedSelections().stream().allMatch(subject ->
                subject.getBlockingReasons().stream().anyMatch(reason -> reason.contains("max subject count"))));
    }

    @Test
    void rejectsHsf302WhenPrj301NotAlreadyPassed() {
        int demoStudentId = findDemoStudentId();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);
        List<Subject> allSubjects = subjectDAO.findAll();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("HSF302"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = planValidator.validatePlan(request, allSubjects, passedSubjectCodes);

        assertFalse(result.isValid());
        assertTrue(result.getRejectedSelections().stream().anyMatch(subject ->
                "HSF302".equals(subject.getSubjectCode())
                        && subject.getBlockingReasons().stream().anyMatch(reason -> reason.contains("PRJ301"))));
    }

    @Test
    void acceptsValidPlanWhenPrerequisitesAreAlreadySatisfied() {
        int demoStudentId = findDemoStudentId();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);
        List<Subject> allSubjects = subjectDAO.findAll();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("PRJ301"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = planValidator.validatePlan(request, allSubjects, passedSubjectCodes);

        assertTrue(result.isValid());
        assertTrue(result.getAcceptedSelections().stream().anyMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));
        assertTrue(result.getRejectedSelections().isEmpty());
    }

    @Test
    void samePlanSelectionDoesNotUnlockAnotherSubject() {
        int demoStudentId = findDemoStudentId();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);
        List<Subject> allSubjects = subjectDAO.findAll();

        PlanRequestDTO request = new PlanRequestDTO();
        request.setStudentId(demoStudentId);
        request.setSelectedSubjectCodes(List.of("PRJ301", "HSF302"));
        request.setMaxSubjects(3);

        PlanValidationResultDTO result = planValidator.validatePlan(request, allSubjects, passedSubjectCodes);

        assertFalse(result.isValid());
        assertTrue(result.getAcceptedSelections().stream().anyMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));

        BlockedSubjectDTO hsf302 = result.getRejectedSelections().stream()
                .filter(subject -> "HSF302".equals(subject.getSubjectCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected HSF302 to be rejected."));

        assertTrue(hsf302.getBlockingReasons().stream().anyMatch(reason -> reason.contains("PRJ301")));
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
