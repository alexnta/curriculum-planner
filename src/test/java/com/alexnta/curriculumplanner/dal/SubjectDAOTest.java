package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubjectDAOTest {

    private final SubjectDAO subjectDAO = new SubjectDAO();

    @Test
    void findAllReturnsSeededSubjects() {
        List<Subject> subjects = subjectDAO.findAll();

        assertFalse(subjects.isEmpty());
        assertEquals(10, subjects.size());
        assertTrue(subjects.stream().anyMatch(subject -> "PRJ301".equals(subject.getCode())));
    }

    @Test
    void findByCodeReturnsPrj301() {
        Subject subject = subjectDAO.findByCode("PRJ301");

        assertNotNull(subject);
        assertEquals("PRJ301", subject.getCode());
        assertEquals("Java Web Application Development", subject.getName());
        assertEquals(4, subject.getTermNo());
        assertTrue(subject.isActive());
    }
}
