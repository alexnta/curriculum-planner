package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.PrerequisiteDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.dto.EligibilityResultDTO;
import com.alexnta.curriculumplanner.dto.MissingRequirementDTO;
import com.alexnta.curriculumplanner.model.Subject;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EligibilityEngineTest {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final EligibilityEngine eligibilityEngine = new EligibilityEngine(new SubjectDAO(), new PrerequisiteDAO());

    @Test
    void evaluatesEligibilityFromPrerequisiteGroups() {
        Subject csd201 = subjectDAO.findByCode("CSD201");
        Subject prj301 = subjectDAO.findByCode("PRJ301");
        Subject hsf302 = subjectDAO.findByCode("HSF302");

        assertNotNull(csd201);
        assertNotNull(prj301);
        assertNotNull(hsf302);

        EligibilityResultDTO csd201Result =
                eligibilityEngine.evaluateSubjectEligibility(csd201.getSubjectId(), Set.of("PRO192"));
        assertTrue(csd201Result.isEligible());
        assertEquals(csd201.getSubjectId(), csd201Result.getTargetSubjectId());
        assertEquals("CSD201", csd201Result.getTargetSubjectCode());
        assertEquals("Data Structures and Algorithms", csd201Result.getTargetSubjectName());
        assertTrue(csd201Result.getMissingRequirements().isEmpty());

        EligibilityResultDTO prj301MissingResult =
                eligibilityEngine.evaluateSubjectEligibility(prj301.getSubjectId(), Set.of("PRO192"));
        assertFalse(prj301MissingResult.isEligible());
        assertEquals(prj301.getSubjectId(), prj301MissingResult.getTargetSubjectId());
        assertEquals("PRJ301", prj301MissingResult.getTargetSubjectCode());
        assertEquals("Java Web Application Development", prj301MissingResult.getTargetSubjectName());
        assertEquals(1, prj301MissingResult.getMissingRequirements().size());

        MissingRequirementDTO missingRequirement = prj301MissingResult.getMissingRequirements().get(0);
        assertEquals("DBI202", missingRequirement.getSubjectCode());

        EligibilityResultDTO prj301EligibleResult =
                eligibilityEngine.evaluateSubjectEligibility(prj301.getSubjectId(), Set.of("PRO192", "DBI202"));
        assertTrue(prj301EligibleResult.isEligible());
        assertTrue(prj301EligibleResult.getMissingRequirements().isEmpty());

        EligibilityResultDTO hsf302Result =
                eligibilityEngine.evaluateSubjectEligibility(hsf302.getSubjectId(), Set.of("PRJ301"));
        assertTrue(hsf302Result.isEligible());
        assertEquals("HSF302", hsf302Result.getTargetSubjectCode());
        assertTrue(hsf302Result.getMissingRequirements().isEmpty());
    }
}
