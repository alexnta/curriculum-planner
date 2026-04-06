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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    private String getUrl() {
        return getValue("CURRICULUM_DB_URL", DEFAULT_URL);
    }

    private String getUser() {
        return getRequiredValue("CURRICULUM_DB_USER");
    }

    private String getPassword() {
        return getRequiredValue("CURRICULUM_DB_PASSWORD");
    }

    private String getValue(String key, String defaultValue) {
        String value = getConfigValue(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    private String getRequiredValue(String key) {
        String value = getConfigValue(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    private String getConfigValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }
        return System.getenv(key);
    }
}
