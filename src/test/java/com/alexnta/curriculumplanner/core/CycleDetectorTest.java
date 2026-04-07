package com.alexnta.curriculumplanner.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CycleDetectorTest {

    private final CycleDetector cycleDetector = new CycleDetector();

    @Test
    void detectsCycleInArtificialGraph() {
        PrerequisiteGraph cyclicGraph = new PrerequisiteGraph();
        cyclicGraph.addEdge(1, 2);
        cyclicGraph.addEdge(2, 3);
        cyclicGraph.addEdge(3, 1);

        assertTrue(cycleDetector.hasCycle(cyclicGraph));
    }

    @Test
    void doesNotFlagAcyclicGraph() {
        PrerequisiteGraph acyclicGraph = new PrerequisiteGraph();
        acyclicGraph.addEdge(1, 2);
        acyclicGraph.addEdge(2, 3);
        acyclicGraph.addNode(4);

        assertFalse(cycleDetector.hasCycle(acyclicGraph));
    }
}
