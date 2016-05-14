package tabu_search;

import tabu_search.longestPath.LongestPathOld;
import org.junit.Test;
import static org.junit.Assert.*;

public class LongestPathTest {
    
    public LongestPathTest() {
    }

    @Test
    public void testCalculate_0args() {        
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; 
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}};
        int[][] pi = {{2, 0, 3, 1}, {3, 0, 1, 2}, {1, 2, 3, 0}};
        
        LongestPathOld.setInput_m(input_m);
        LongestPathOld.setInput_v(input_v);
        LongestPathOld.setM(3);
        LongestPathOld.setN(4);
        
        LongestPathOld instance = new LongestPathOld(pi);
        
        
        int expResult = 32;
        int result = instance.calculate();
        assertEquals(expResult, result);
    }
    
}
