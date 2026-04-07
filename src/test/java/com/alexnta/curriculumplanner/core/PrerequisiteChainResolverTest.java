package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.PrerequisiteDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrerequisiteChainResolverTest {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
    private final GraphBuilder graphBuilder = new GraphBuilder();
    private final PrerequisiteChainResolver chainResolver = new PrerequisiteChainResolver();

    @Test
    void resolvesUpstreamPrerequisitesForSeededSubjects() {
        Subject prj301 = subjectDAO.findByCode("PRJ301");
        Subject hsf302 = subjectDAO.findByCode("HSF302");
        Subject pro192 = subjectDAO.findByCode("PRO192");
        Subject dbi202 = subjectDAO.findByCode("DBI202");

        assertNotNull(prj301);
        assertNotNull(hsf302);
        assertNotNull(pro192);
        assertNotNull(dbi202);

        PrerequisiteGraph graph = graphBuilder.build(loadAllPrerequisiteGroups());

        Set<Integer> prj301Chain = chainResolver.resolveUpstreamPrerequisites(graph, prj301.getSubjectId());
        assertEquals(Set.of(pro192.getSubjectId(), dbi202.getSubjectId()), prj301Chain);

        Set<Integer> hsf302Chain = chainResolver.resolveUpstreamPrerequisites(graph, hsf302.getSubjectId());
        assertTrue(hsf302Chain.contains(prj301.getSubjectId()));
        assertTrue(hsf302Chain.contains(pro192.getSubjectId()));
        assertTrue(hsf302Chain.contains(dbi202.getSubjectId()));
    }

    private List<PrerequisiteGroup> loadAllPrerequisiteGroups() {
        List<PrerequisiteGroup> groups = new ArrayList<>();
        for (Subject subject : subjectDAO.findAll()) {
            groups.addAll(prerequisiteDAO.findGroupsBySubjectId(subject.getSubjectId()));
        }
        return groups;
    }
}
