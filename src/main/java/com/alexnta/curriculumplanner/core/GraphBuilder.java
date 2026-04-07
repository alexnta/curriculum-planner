package com.alexnta.curriculumplanner.core;

import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.PrerequisiteItem;

import java.util.List;

public class GraphBuilder {

    public PrerequisiteGraph build(List<PrerequisiteGroup> prerequisiteGroups) {
        PrerequisiteGraph graph = new PrerequisiteGraph();

        for (PrerequisiteGroup group : prerequisiteGroups) {
            graph.addNode(group.getSubjectId());
            for (PrerequisiteItem item : group.getItems()) {
                graph.addEdge(item.getPrerequisiteSubjectId(), group.getSubjectId());
            }
        }

        return graph;
    }
}
