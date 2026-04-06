USE CurriculumPlanner;
GO

DECLARE @demoStudentId INT = (SELECT student_id FROM dbo.students WHERE student_code = 'DEMO001');

INSERT INTO dbo.student_subject_results (student_id, subject_id, status, completed_term)
SELECT
    @demoStudentId,
    subject_id,
    status,
    completed_term
FROM (
    VALUES
        ('PRF192', 'PASSED', 'SP24'),
        ('PRO192', 'PASSED', 'SU24'),
        ('DBI202', 'PASSED', 'FA24'),
        ('JPD113', 'PASSED', 'SP24')
) AS seeded_results(code, status, completed_term)
INNER JOIN dbo.subjects s
    ON s.code = seeded_results.code;
GO
