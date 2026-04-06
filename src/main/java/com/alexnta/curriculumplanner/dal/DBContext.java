package com.alexnta.curriculumplanner.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("SQL Server JDBC driver not found on the runtime classpath.", ex);
        }
    }

    private static final String DEFAULT_URL =
            "jdbc:sqlserver://localhost:1433;databaseName=CurriculumPlanner;encrypt=true;trustServerCertificate=true";
    private static final String DEFAULT_USER = "sa";
    private static final String DEFAULT_PASSWORD = "12345";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    private String getUrl() {
        return getValue("CURRICULUM_DB_URL", DEFAULT_URL);
    }

    private String getUser() {
        return getValue("CURRICULUM_DB_USER", DEFAULT_USER);
    }

    private String getPassword() {
        return getValue("CURRICULUM_DB_PASSWORD", DEFAULT_PASSWORD);
    }

    private String getValue(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
