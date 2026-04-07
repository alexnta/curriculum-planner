package com.alexnta.curriculumplanner.service;

import com.alexnta.curriculumplanner.dal.PrerequisiteDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.Subject;

import java.util.List;

public class SubjectService {

    private final SubjectDAO subjectDAO;
    private final PrerequisiteDAO prerequisiteDAO;

    public SubjectService() {
        this(new SubjectDAO(), new PrerequisiteDAO());
    }

    public SubjectService(SubjectDAO subjectDAO, PrerequisiteDAO prerequisiteDAO) {
        this.subjectDAO = subjectDAO;
        this.prerequisiteDAO = prerequisiteDAO;
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.findAll();
    }

    public Subject getSubjectById(int subjectId) {
        return subjectDAO.findById(subjectId);
    }

    public Subject getSubjectByCode(String code) {
        return subjectDAO.findByCode(code);
    }

    public List<PrerequisiteGroup> getPrerequisiteGroupsBySubjectId(int subjectId) {
        return prerequisiteDAO.findGroupsBySubjectId(subjectId);
    }
}
