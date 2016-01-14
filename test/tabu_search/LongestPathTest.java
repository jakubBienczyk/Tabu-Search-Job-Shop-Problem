package tabu_search;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LongestPathTest {
    
    public LongestPathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testCalculate_0args() {
        System.out.println("calculate");
        
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; 
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}};
        int[][] pi = {{2, 0, 3, 1}, {3, 0, 1, 2}, {1, 2, 3, 0}};
        
        LongestPath.setInput_m(input_m);
        LongestPath.setInput_v(input_v);
        LongestPath.setM(3);
        LongestPath.setN(4);
        
        LongestPath instance = new LongestPath(pi);
        
        
        int expResult = 32;
        int result = instance.calculate();
        assertEquals(expResult, result);
    }
    
}
