package tabu_search.neighbors;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import tabu_search.graph.Graph;
import tabu_search.graph.GraphChanger;
import tabu_search.graph.OperationVertex;
import tabu_search.input.Input;
import tabu_search.input.MockInput;
import tabu_search.longestPath.LongestPathImpl;

public class NeighborsGeneratorImplTest {

    private NeighborsGenerator generator;
    private List<Neighbor> neighbors;

    public NeighborsGeneratorImplTest() {
        Graph graph = generateMockGraph();
        Input input = new MockInput();
        List<OperationVertex> longestPath =generateLongestPath(input, graph);
        generator = new NeighborsGeneratorImpl(graph, longestPath);
        generator.calculate();
        neighbors = generator.getNeighbors();
    }

    @Test
    public void testNeighborsSize() {
        System.out.println(neighbors);
        assertTrue(neighbors.size() == 4);
    }
    
    @Test
    public void testNeighbors() {
        assertTrue(neighbors.stream().anyMatch(x -> x.getFirstVertexIndexOnLongestPath() == 2));
        assertTrue(neighbors.stream().anyMatch(x -> x.getSecondVertexIndexOnLongestPath() == 3));
        assertFalse(neighbors.stream().anyMatch(x -> x.getFirstVertexIndexOnLongestPath() == 1));
    }
    
    private Graph generateMockGraph() {
        int[][] graphTable = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
        return new GraphChanger(new MockInput()).fromTable(graphTable);
    }
    
    private List<OperationVertex> generateLongestPath(Input input, Graph graph) {
        LongestPathImpl longestPathImpl = new LongestPathImpl(graph, input.getNumberOfMachines(), input.getNumberOfTasks());
        longestPathImpl.calculate();
        return longestPathImpl.getPath();
    }

}
