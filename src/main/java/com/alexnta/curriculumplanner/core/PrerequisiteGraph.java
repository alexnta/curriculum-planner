package com.alexnta.curriculumplanner.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PrerequisiteGraph {

    private final Map<Integer, Set<Integer>> prerequisiteToDependents = new LinkedHashMap<>();
    private final Map<Integer, Set<Integer>> subjectToPrerequisites = new LinkedHashMap<>();

    public void addNode(int subjectId) {
        prerequisiteToDependents.computeIfAbsent(subjectId, ignored -> new LinkedHashSet<>());
        subjectToPrerequisites.computeIfAbsent(subjectId, ignored -> new LinkedHashSet<>());
    }

    public void addEdge(int prerequisiteSubjectId, int dependentSubjectId) {
        addNode(prerequisiteSubjectId);
        addNode(dependentSubjectId);
        prerequisiteToDependents.get(prerequisiteSubjectId).add(dependentSubjectId);
        subjectToPrerequisites.get(dependentSubjectId).add(prerequisiteSubjectId);
    }

    public Set<Integer> getDependents(int subjectId) {
        return Collections.unmodifiableSet(
                prerequisiteToDependents.getOrDefault(subjectId, Collections.emptySet())
        );
    }

    public Set<Integer> getPrerequisites(int subjectId) {
        return Collections.unmodifiableSet(
                subjectToPrerequisites.getOrDefault(subjectId, Collections.emptySet())
        );
    }

    public Set<Integer> getSubjectIds() {
        Set<Integer> subjectIds = new LinkedHashSet<>();
        subjectIds.addAll(prerequisiteToDependents.keySet());
        subjectIds.addAll(subjectToPrerequisites.keySet());
        return Collections.unmodifiableSet(subjectIds);
    }
}
