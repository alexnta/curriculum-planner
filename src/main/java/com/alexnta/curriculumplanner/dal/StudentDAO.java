package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    private final DBContext dbContext = new DBContext();

    public Student findById(int id) {
        String sql = """
                SELECT student_id, student_code, full_name
                FROM dbo.students
                WHERE student_id = ?
                """;

        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student();
                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setStudentCode(resultSet.getString("student_code"));
                    student.setFullName(resultSet.getString("full_name"));
                    return student;
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load student by id.", ex);
        }
        return null;
    }
}
