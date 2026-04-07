package com.alexnta.curriculumplanner.service;

import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.PrerequisiteItem;
import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubjectServiceTest {

    private final SubjectService subjectService = new SubjectService();

    @Test
    void getAllSubjectsReturnsSeededSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();

        assertFalse(subjects.isEmpty());
        assertTrue(subjects.stream().anyMatch(subject -> "PRJ301".equals(subject.getCode())));
    }

    @Test
    void getSubjectByCodeReturnsPrj301() {
        Subject subject = subjectService.getSubjectByCode("PRJ301");

        assertNotNull(subject);
        assertEquals("PRJ301", subject.getCode());
        assertEquals("Java Web Application Development", subject.getName());
        assertEquals(4, subject.getTermNo());
    }

    @Test
    void getPrerequisiteGroupsBySubjectIdReturnsExpectedPrj301Structure() {
        Subject prj301 = subjectService.getSubjectByCode("PRJ301");
        Subject pro192 = subjectService.getSubjectByCode("PRO192");
        Subject dbi202 = subjectService.getSubjectByCode("DBI202");

        assertNotNull(prj301);
        assertNotNull(pro192);
        assertNotNull(dbi202);

        List<PrerequisiteGroup> groups = subjectService.getPrerequisiteGroupsBySubjectId(prj301.getSubjectId());

        assertEquals(1, groups.size());

        PrerequisiteGroup group = groups.get(0);
        assertEquals("Programming and database foundations", group.getGroupName());
        assertEquals(2, group.getMinRequired());
        assertEquals(2, group.getItems().size());

        Set<Integer> prerequisiteSubjectIds = group.getItems().stream()
                .map(PrerequisiteItem::getPrerequisiteSubjectId)
                .collect(Collectors.toSet());

        assertEquals(Set.of(pro192.getSubjectId(), dbi202.getSubjectId()), prerequisiteSubjectIds);
    }
}
