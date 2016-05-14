package tabu_search;

import tabu_search.heuristic.StarterHeuristics;
import org.junit.Test;
import static org.junit.Assert.*;

public class StarterHeuristicsTest {
    
    /**
     * Test of get method, of class StarterHeuristics.
     */
    @Test
    public void testGet() {
        System.out.println("get");
       
        
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; 
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}};
        
        StarterHeuristics instance = new StarterHeuristics(4, 3, input_v, input_m);
        assertNotNull(instance.get());
    }
    
}
