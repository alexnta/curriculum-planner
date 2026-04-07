package com.alexnta.curriculumplanner.service;

import com.alexnta.curriculumplanner.dal.StudentResultDAO;
import com.alexnta.curriculumplanner.model.StudentResult;

import java.util.List;
import java.util.Set;

public class StudentProgressService {

    private final StudentResultDAO studentResultDAO;

    public StudentProgressService() {
        this(new StudentResultDAO());
    }

    public StudentProgressService(StudentResultDAO studentResultDAO) {
        this.studentResultDAO = studentResultDAO;
    }

    public List<StudentResult> getStudentResults(int studentId) {
        return studentResultDAO.findByStudentId(studentId);
    }

    public Set<String> getPassedSubjectCodes(int studentId) {
        return studentResultDAO.findPassedSubjectCodes(studentId);
    }
}
