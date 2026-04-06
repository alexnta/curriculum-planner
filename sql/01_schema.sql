IF DB_ID('CurriculumPlanner') IS NULL
BEGIN
    CREATE DATABASE CurriculumPlanner;
END;
GO

USE CurriculumPlanner;
GO

IF OBJECT_ID('dbo.student_subject_results', 'U') IS NOT NULL
    DROP TABLE dbo.student_subject_results;
IF OBJECT_ID('dbo.prerequisite_group_items', 'U') IS NOT NULL
    DROP TABLE dbo.prerequisite_group_items;
IF OBJECT_ID('dbo.prerequisite_groups', 'U') IS NOT NULL
    DROP TABLE dbo.prerequisite_groups;
IF OBJECT_ID('dbo.students', 'U') IS NOT NULL
    DROP TABLE dbo.students;
IF OBJECT_ID('dbo.subjects', 'U') IS NOT NULL
    DROP TABLE dbo.subjects;
GO

CREATE TABLE dbo.subjects (
    subject_id INT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name NVARCHAR(255) NOT NULL,
    term_no INT NOT NULL,
    is_active BIT NOT NULL DEFAULT 1
);
GO

CREATE TABLE dbo.students (
    student_id INT IDENTITY(1,1) PRIMARY KEY,
    student_code VARCHAR(30) NOT NULL UNIQUE,
    full_name NVARCHAR(150) NOT NULL
);
GO

CREATE TABLE dbo.prerequisite_groups (
    prerequisite_group_id INT IDENTITY(1,1) PRIMARY KEY,
    subject_id INT NOT NULL,
    group_name NVARCHAR(100) NOT NULL,
    min_required INT NOT NULL DEFAULT 1,
    CONSTRAINT FK_prerequisite_groups_subject
        FOREIGN KEY (subject_id) REFERENCES dbo.subjects(subject_id)
);
GO

CREATE TABLE dbo.prerequisite_group_items (
    prerequisite_group_item_id INT IDENTITY(1,1) PRIMARY KEY,
    prerequisite_group_id INT NOT NULL,
    prerequisite_subject_id INT NOT NULL,
    CONSTRAINT FK_prerequisite_group_items_group
        FOREIGN KEY (prerequisite_group_id) REFERENCES dbo.prerequisite_groups(prerequisite_group_id),
    CONSTRAINT FK_prerequisite_group_items_subject
        FOREIGN KEY (prerequisite_subject_id) REFERENCES dbo.subjects(subject_id),
    CONSTRAINT UQ_prerequisite_group_item UNIQUE (prerequisite_group_id, prerequisite_subject_id)
);
GO

CREATE TABLE dbo.student_subject_results (
    student_subject_result_id INT IDENTITY(1,1) PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    completed_term VARCHAR(20) NULL,
    CONSTRAINT FK_student_subject_results_student
        FOREIGN KEY (student_id) REFERENCES dbo.students(student_id),
    CONSTRAINT FK_student_subject_results_subject
        FOREIGN KEY (subject_id) REFERENCES dbo.subjects(subject_id),
    CONSTRAINT UQ_student_subject_result UNIQUE (student_id, subject_id),
    CONSTRAINT CK_student_subject_results_status
        CHECK (status IN ('PASSED', 'FAILED', 'IN_PROGRESS'))
);
GO
