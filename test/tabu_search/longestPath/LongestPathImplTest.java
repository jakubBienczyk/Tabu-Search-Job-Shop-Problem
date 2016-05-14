package tabu_search.longestPath;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import tabu_search.graph.Graph;
import tabu_search.graph.GraphChanger;
import tabu_search.graph.OperationVertex;
import tabu_search.input.Input;
import tabu_search.input.MockInput;

public class LongestPathImplTest {
    
    private LongestPathImpl longestPathImpl;
    
    public LongestPathImplTest() {
        
    }
    
    @Before
    public void setLongestPathImpl(){
        Input input = new MockInput();
        longestPathImpl = new LongestPathImpl(input.getNumberOfMachines(), input.getNumberOfTasks());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullGraphExceptionWhenGraphIsNotSet() throws NullPointerException{
        longestPathImpl.calculate();
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPathExceptionWhenSolutionIsNotCalculated() throws NullPointerException{
        longestPathImpl.getPath();
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPathLengthExceptionWhenSolutionIsNotCalculated() throws NullPointerException{
        longestPathImpl.getPathLength();
    }

    @Test
    public void testCalculatePathLength() {
        addGraphToLongestPath(longestPathImpl);
        longestPathImpl.calculate();
        
        assertEquals(70, longestPathImpl.getPathLength());
    }
    
    @Test
    public void testCalculatePath() {
        addGraphToLongestPath(longestPathImpl);
        longestPathImpl.calculate();
        List<OperationVertex> path = longestPathImpl.getPath();
        
        int pathLength = 0;
        for(OperationVertex op : path){
            pathLength = pathLength + op.getTime();
        }
        
        assertEquals(70, pathLength);
    }
    
    private void addGraphToLongestPath(LongestPath longestPath) {
        int[][] graphTable = {{0,1,2,3},{0,1,2,3},{0,1,2,3}};
        Graph graph = new GraphChanger(new MockInput()).fromTable(graphTable);
        longestPath.setGraph(graph);
        
    }
    
    
}
