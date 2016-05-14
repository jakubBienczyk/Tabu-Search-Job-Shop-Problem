package tabu_search.longestPath;

import org.junit.Test;
import static org.junit.Assert.*;
import tabu_search.graph.Graph;
import tabu_search.graph.GraphChanger;
import tabu_search.graph.OperationVertex;
import tabu_search.input.MockInput;

public class GraphChangerTest {
    
    public GraphChangerTest() {
    }

    @Test
    public void testSomeMethod() {
        GraphChanger changer = new GraphChanger(new MockInput());
        int[][] graphTable = {{0,1,2,3},{0,1,2,3},{0,1,2,3}};
        Graph graph = changer.fromTable(graphTable);
        assertTrue(graph.getFirstOperationsOnMachines().length == 3);
        OperationVertex operation = graph.getFirstOperationsOnMachines()[0];
        assertTrue(operation.getMachine() == 0);
        assertTrue(operation.getNextOnMachine().getMachine() == 0);
        assertTrue(operation.getTask() == 0);
        assertTrue(operation.getNextOnTask().getTask() == 0);
    }
    
}
