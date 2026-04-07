package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.DBContext;
import com.alexnta.curriculumplanner.dal.StudentResultDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.dto.BlockedSubjectDTO;
import com.alexnta.curriculumplanner.dto.RecommendedSubjectDTO;
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

class RecommendationEngineTest {

    private final DBContext dbContext = new DBContext();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final StudentResultDAO studentResultDAO = new StudentResultDAO();
    private final RecommendationEngine recommendationEngine = new RecommendationEngine(new EligibilityEngine());

    @Test
    void returnsEligibleSubjectsForDemoStudentAndExcludesPassedSubjects() {
        int demoStudentId = findDemoStudentId();
        List<Subject> allSubjects = subjectDAO.findAll();
        Set<String> passedSubjectCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);

        List<RecommendedSubjectDTO> recommendedSubjects =
                recommendationEngine.getRecommendedSubjects(allSubjects, passedSubjectCodes);

        assertFalse(recommendedSubjects.isEmpty());
        assertTrue(recommendedSubjects.stream().anyMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));
        assertTrue(recommendedSubjects.stream().noneMatch(subject -> passedSubjectCodes.contains(subject.getSubjectCode())));
    }

    @Test
    void returnsBlockedSubjectsWithHumanReadableReasons() {
        List<Subject> allSubjects = subjectDAO.findAll();
        Set<String> passedSubjectCodes = Set.of("PRO192");

        List<RecommendedSubjectDTO> recommendedSubjects =
                recommendationEngine.getRecommendedSubjects(allSubjects, passedSubjectCodes);
        List<BlockedSubjectDTO> blockedSubjects =
                recommendationEngine.getBlockedSubjects(allSubjects, passedSubjectCodes);

        assertTrue(recommendedSubjects.stream().noneMatch(subject -> "PRJ301".equals(subject.getSubjectCode())));

        BlockedSubjectDTO prj301 = blockedSubjects.stream()
                .filter(subject -> "PRJ301".equals(subject.getSubjectCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected PRJ301 to be blocked."));

        assertFalse(prj301.getBlockingReasons().isEmpty());
        assertTrue(prj301.getBlockingReasons().stream().anyMatch(reason -> reason.contains("DBI202")));
        assertTrue(prj301.getBlockingReasons().stream().allMatch(reason -> reason.startsWith("Missing prerequisite: ")));
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
