package com.alexnta.curriculumplanner.core;

import java.util.HashSet;
import java.util.Set;

public class CycleDetector {

    public boolean hasCycle(PrerequisiteGraph graph) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> activePath = new HashSet<>();

        for (Integer subjectId : graph.getSubjectIds()) {
            if (detectCycle(graph, subjectId, visited, activePath)) {
                return true;
            }
        }

        return false;
    }

    private boolean detectCycle(PrerequisiteGraph graph, int subjectId, Set<Integer> visited, Set<Integer> activePath) {
        if (activePath.contains(subjectId)) {
            return true;
        }
        if (visited.contains(subjectId)) {
            return false;
        }

        visited.add(subjectId);
        activePath.add(subjectId);

        for (Integer dependentId : graph.getDependents(subjectId)) {
            if (detectCycle(graph, dependentId, visited, activePath)) {
                return true;
            }
        }

        activePath.remove(subjectId);
        return false;
    }
}
