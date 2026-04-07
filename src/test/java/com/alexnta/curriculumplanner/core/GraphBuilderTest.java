package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.PrerequisiteDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphBuilderTest {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
    private final GraphBuilder graphBuilder = new GraphBuilder();

    @Test
    void buildsEdgesFromSeededPrerequisites() {
        Subject prj301 = subjectDAO.findByCode("PRJ301");
        Subject pro192 = subjectDAO.findByCode("PRO192");
        Subject dbi202 = subjectDAO.findByCode("DBI202");

        assertNotNull(prj301);
        assertNotNull(pro192);
        assertNotNull(dbi202);

        PrerequisiteGraph graph = graphBuilder.build(loadAllPrerequisiteGroups());

        Set<Integer> prj301Prerequisites = graph.getPrerequisites(prj301.getSubjectId());
        assertTrue(prj301Prerequisites.contains(pro192.getSubjectId()));
        assertTrue(prj301Prerequisites.contains(dbi202.getSubjectId()));

        assertTrue(graph.getDependents(pro192.getSubjectId()).contains(prj301.getSubjectId()));
        assertTrue(graph.getDependents(dbi202.getSubjectId()).contains(prj301.getSubjectId()));
    }

    private List<PrerequisiteGroup> loadAllPrerequisiteGroups() {
        List<PrerequisiteGroup> groups = new ArrayList<>();
        for (Subject subject : subjectDAO.findAll()) {
            groups.addAll(prerequisiteDAO.findGroupsBySubjectId(subject.getSubjectId()));
        }
        return groups;
    }
}
