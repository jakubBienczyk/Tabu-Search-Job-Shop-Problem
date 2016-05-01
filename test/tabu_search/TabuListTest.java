package tabu_search;

import org.junit.Test;
import static org.junit.Assert.*;

public class TabuListTest {
    
    @Test
    public void testContain() {
        System.out.println("contain");
        
        TabuList tabu = new TabuList();
        tabu.setMax_size(3);
        
        tabu.add(1, 2);
        tabu.add(3, 4);
        tabu.add(5, 6);
        
        assertTrue(tabu.contains(1, 2));
        assertFalse(tabu.contains(1, 4));
        assertEquals(tabu.position(1, 2), 0);
        assertEquals(tabu.position(3, 4), 1);
        
        tabu.add(7, 8);
        
        assertTrue(tabu.contains(5, 6));
        assertTrue(tabu.contains(7, 8));
        assertFalse(tabu.contains(1, 2));
    }
    
}
