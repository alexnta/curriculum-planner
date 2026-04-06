package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.Student;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentDAOTest {

    private final DBContext dbContext = new DBContext();
    private final StudentDAO studentDAO = new StudentDAO();

    @Test
    void findByIdReturnsDemoStudent() {
        int demoStudentId = findDemoStudentId();

        Student student = studentDAO.findById(demoStudentId);

        assertNotNull(student);
        assertEquals(demoStudentId, student.getStudentId());
        assertEquals("DEMO001", student.getStudentCode());
        assertEquals("Demo Student", student.getFullName());
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
