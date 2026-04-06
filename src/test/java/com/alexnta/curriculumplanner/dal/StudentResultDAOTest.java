package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.ResultStatus;
import com.alexnta.curriculumplanner.model.StudentResult;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentResultDAOTest {

    private final DBContext dbContext = new DBContext();
    private final StudentResultDAO studentResultDAO = new StudentResultDAO();

    @Test
    void findByStudentIdReturnsDemoStudentResults() {
        int demoStudentId = findDemoStudentId();

        List<StudentResult> results = studentResultDAO.findByStudentId(demoStudentId);

        assertFalse(results.isEmpty());
        assertEquals(4, results.size());
        assertTrue(results.stream().allMatch(result -> result.getStudentId() == demoStudentId));
        assertTrue(results.stream().allMatch(result -> result.getStatus() == ResultStatus.PASSED));
    }

    @Test
    void findPassedSubjectCodesReturnsExpectedPassedSet() {
        int demoStudentId = findDemoStudentId();

        Set<String> passedCodes = studentResultDAO.findPassedSubjectCodes(demoStudentId);

        assertEquals(Set.of("PRF192", "PRO192", "DBI202", "JPD113"), passedCodes);
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
