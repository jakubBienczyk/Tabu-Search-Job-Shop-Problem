package tabu_search.heuristic;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import tabu_search.input.MockInput;

public class SimpleGreedyHeuristicTest {

    private SimpleGreedyHeuristic testedHeuristic;

    public SimpleGreedyHeuristicTest() {
        testedHeuristic = new SimpleGreedyHeuristic(new MockInput());
        testedHeuristic.generateSolution();
    }

    @Test
    public void testGetGraphNotNull() {
        assertNotNull(testedHeuristic.getGraph());
    }

    @Test
    public void testGraph() {
        int[][] graph = testedHeuristic.getGraph();
        int[][] expectedGraph = {{0, 1, 2, 3}, {3, 0, 1, 2}, {2, 3, 1, 0}};
        Assert.assertArrayEquals(graph, expectedGraph);
    }

}
