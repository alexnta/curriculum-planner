USE CurriculumPlanner;
GO

DECLARE @subject_PRO192 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'PRO192');
DECLARE @subject_CSD201 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'CSD201');
DECLARE @subject_DBI202 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'DBI202');
DECLARE @subject_PRJ301 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'PRJ301');
DECLARE @subject_HSF302 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'HSF302');
DECLARE @subject_JPD113 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'JPD113');
DECLARE @subject_JPD123 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'JPD123');
DECLARE @subject_SWE202c INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'SWE202c');
DECLARE @subject_IOT102 INT = (SELECT subject_id FROM dbo.subjects WHERE code = 'IOT102');

DECLARE @group_CSD201 INT;
DECLARE @group_PRJ301 INT;
DECLARE @group_HSF302 INT;
DECLARE @group_JPD123 INT;
DECLARE @group_SWE202c INT;
DECLARE @group_IOT102 INT;

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_CSD201, 'Core programming prerequisite', 1);
SET @group_CSD201 = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES (@group_CSD201, @subject_PRO192);

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_PRJ301, 'Programming and database foundations', 2);
SET @group_PRJ301 = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES
    (@group_PRJ301, @subject_PRO192),
    (@group_PRJ301, @subject_DBI202);

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_HSF302, 'Project prerequisite', 1);
SET @group_HSF302 = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES (@group_HSF302, @subject_PRJ301);

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_JPD123, 'Japanese sequence prerequisite', 1);
SET @group_JPD123 = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES (@group_JPD123, @subject_JPD113);

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_SWE202c, 'Software engineering prerequisites', 2);
SET @group_SWE202c = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES
    (@group_SWE202c, @subject_CSD201),
    (@group_SWE202c, @subject_DBI202);

INSERT INTO dbo.prerequisite_groups (subject_id, group_name, min_required)
VALUES (@subject_IOT102, 'Programming readiness', 1);
SET @group_IOT102 = SCOPE_IDENTITY();

INSERT INTO dbo.prerequisite_group_items (prerequisite_group_id, prerequisite_subject_id)
VALUES (@group_IOT102, @subject_PRO192);
GO
