package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    private final DBContext dbContext = new DBContext();

    public List<Subject> findAll() {
        String sql = """
                SELECT subject_id, code, name, term_no, is_active
                FROM dbo.subjects
                ORDER BY subject_id
                """;

        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                subjects.add(mapSubject(resultSet));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load subjects.", ex);
        }
        return subjects;
    }

    public Subject findById(int id) {
        String sql = """
                SELECT subject_id, code, name, term_no, is_active
                FROM dbo.subjects
                WHERE subject_id = ?
                """;

        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSubject(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load subject by id.", ex);
        }
        return null;
    }

    public Subject findByCode(String code) {
        String sql = """
                SELECT subject_id, code, name, term_no, is_active
                FROM dbo.subjects
                WHERE code = ?
                """;

        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSubject(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load subject by code.", ex);
        }
        return null;
    }

    private Subject mapSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(resultSet.getInt("subject_id"));
        subject.setCode(resultSet.getString("code"));
        subject.setName(resultSet.getString("name"));
        subject.setTermNo(resultSet.getInt("term_no"));
        subject.setActive(resultSet.getBoolean("is_active"));
        return subject;
    }
}
