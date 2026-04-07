package com.alexnta.curriculumplanner.core;

import java.util.LinkedHashSet;
import java.util.Set;

public class PrerequisiteChainResolver {

    public Set<Integer> resolveUpstreamPrerequisites(PrerequisiteGraph graph, int targetSubjectId) {
        Set<Integer> resolved = new LinkedHashSet<>();
        collectPrerequisites(graph, targetSubjectId, resolved);
        return resolved;
    }

    private void collectPrerequisites(PrerequisiteGraph graph, int subjectId, Set<Integer> resolved) {
        for (Integer prerequisiteId : graph.getPrerequisites(subjectId)) {
            if (resolved.add(prerequisiteId)) {
                collectPrerequisites(graph, prerequisiteId, resolved);
            }
        }
    }
}
