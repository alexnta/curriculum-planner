package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.dal.PrerequisiteDAO;
import com.alexnta.curriculumplanner.dal.SubjectDAO;
import com.alexnta.curriculumplanner.dto.EligibilityResultDTO;
import com.alexnta.curriculumplanner.dto.MissingRequirementDTO;
import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.PrerequisiteItem;
import com.alexnta.curriculumplanner.model.Subject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EligibilityEngine {

    private final SubjectDAO subjectDAO;
    private final PrerequisiteDAO prerequisiteDAO;

    public EligibilityEngine() {
        this(new SubjectDAO(), new PrerequisiteDAO());
    }

    public EligibilityEngine(SubjectDAO subjectDAO, PrerequisiteDAO prerequisiteDAO) {
        this.subjectDAO = subjectDAO;
        this.prerequisiteDAO = prerequisiteDAO;
    }

    public EligibilityResultDTO evaluateSubjectEligibility(int subjectId, Set<String> passedSubjectCodes) {
        Subject targetSubject = subjectDAO.findById(subjectId);
        if (targetSubject == null) {
            throw new IllegalArgumentException("Subject not found: " + subjectId);
        }

        List<PrerequisiteGroup> groups = prerequisiteDAO.findGroupsBySubjectId(subjectId);
        if (groups.isEmpty()) {
            return new EligibilityResultDTO(
                    targetSubject.getSubjectId(),
                    targetSubject.getCode(),
                    targetSubject.getName(),
                    true,
                    new ArrayList<>()
            );
        }

        Map<String, MissingRequirementDTO> missingByCode = new LinkedHashMap<>();
        boolean eligible = true;

        for (PrerequisiteGroup group : groups) {
            int passedCount = 0;
            for (PrerequisiteItem item : group.getItems()) {
                Subject prerequisiteSubject = subjectDAO.findById(item.getPrerequisiteSubjectId());
                if (prerequisiteSubject != null
                        && passedSubjectCodes.contains(prerequisiteSubject.getCode())) {
                    passedCount++;
                }
            }

            if (passedCount < group.getMinRequired()) {
                eligible = false;
                for (PrerequisiteItem item : group.getItems()) {
                    Subject prerequisiteSubject = subjectDAO.findById(item.getPrerequisiteSubjectId());
                    if (prerequisiteSubject != null
                            && !passedSubjectCodes.contains(prerequisiteSubject.getCode())) {
                        missingByCode.putIfAbsent(
                                prerequisiteSubject.getCode(),
                                new MissingRequirementDTO(
                                        prerequisiteSubject.getSubjectId(),
                                        prerequisiteSubject.getCode(),
                                        prerequisiteSubject.getName(),
                                        group.getGroupName()
                                )
                        );
                    }
                }
            }
        }

        return new EligibilityResultDTO(
                targetSubject.getSubjectId(),
                targetSubject.getCode(),
                targetSubject.getName(),
                eligible,
                new ArrayList<>(missingByCode.values())
        );
    }
}
