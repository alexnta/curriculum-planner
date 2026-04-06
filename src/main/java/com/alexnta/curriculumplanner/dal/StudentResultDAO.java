package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.ResultStatus;
import com.alexnta.curriculumplanner.model.StudentResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StudentResultDAO {

    private final DBContext dbContext = new DBContext();

    public List<StudentResult> findByStudentId(int studentId) {
        String sql = """
                SELECT student_subject_result_id, student_id, subject_id, status, completed_term
                FROM dbo.student_subject_results
                WHERE student_id = ?
                ORDER BY student_subject_result_id
                """;

        List<StudentResult> results = new ArrayList<>();
        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapStudentResult(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load student results.", ex);
        }
        return results;
    }

    public Set<String> findPassedSubjectCodes(int studentId) {
        String sql = """
                SELECT s.code
                FROM dbo.student_subject_results r
                INNER JOIN dbo.subjects s
                    ON s.subject_id = r.subject_id
                WHERE r.student_id = ?
                  AND r.status = ?
                ORDER BY s.subject_id
                """;

        Set<String> passedCodes = new LinkedHashSet<>();
        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setString(2, ResultStatus.PASSED.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    passedCodes.add(resultSet.getString("code"));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load passed subject codes.", ex);
        }
        return passedCodes;
    }

    private StudentResult mapStudentResult(ResultSet resultSet) throws SQLException {
        StudentResult studentResult = new StudentResult();
        studentResult.setStudentSubjectResultId(resultSet.getInt("student_subject_result_id"));
        studentResult.setStudentId(resultSet.getInt("student_id"));
        studentResult.setSubjectId(resultSet.getInt("subject_id"));
        studentResult.setStatus(ResultStatus.fromDatabaseValue(resultSet.getString("status")));
        studentResult.setCompletedTerm(resultSet.getString("completed_term"));
        return studentResult;
    }
}
