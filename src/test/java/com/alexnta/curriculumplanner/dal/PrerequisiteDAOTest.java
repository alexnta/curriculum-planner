package com.alexnta.curriculumplanner.dal;

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

class PrerequisiteDAOTest {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();

    @Test
    void findGroupsBySubjectIdReturnsPrj301Structure() {
        Subject prj301 = subjectDAO.findByCode("PRJ301");
        Subject pro192 = subjectDAO.findByCode("PRO192");
        Subject dbi202 = subjectDAO.findByCode("DBI202");

        assertNotNull(prj301);
        assertNotNull(pro192);
        assertNotNull(dbi202);

        List<PrerequisiteGroup> groups = prerequisiteDAO.findGroupsBySubjectId(prj301.getSubjectId());

        assertFalse(groups.isEmpty());
        assertEquals(1, groups.size());

        PrerequisiteGroup group = groups.get(0);
        assertEquals(prj301.getSubjectId(), group.getSubjectId());
        assertEquals("Programming and database foundations", group.getGroupName());
        assertEquals(2, group.getMinRequired());
        assertEquals(2, group.getItems().size());

        Set<Integer> prerequisiteSubjectIds = group.getItems().stream()
                .map(PrerequisiteItem::getPrerequisiteSubjectId)
                .collect(Collectors.toSet());

        assertEquals(Set.of(pro192.getSubjectId(), dbi202.getSubjectId()), prerequisiteSubjectIds);
    }
}
