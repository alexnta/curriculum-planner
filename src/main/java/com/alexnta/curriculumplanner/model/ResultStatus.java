package com.alexnta.curriculumplanner.model;

public enum ResultStatus {
    PASSED,
    FAILED,
    IN_PROGRESS;

    public static ResultStatus fromDatabaseValue(String value) {
        return ResultStatus.valueOf(value);
    }
}
