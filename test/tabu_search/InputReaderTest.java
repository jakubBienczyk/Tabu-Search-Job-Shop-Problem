package tabu_search;

import tabu_search.input.InputReader;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;


public class InputReaderTest {
    
    final private String testedFile = "ta00_test";
    final private InputReader inputReader= new InputReader(testedFile);;
    final private int numberOfMachines = 15;
    final private int numberOfTasks = 20;
    final private int knownUpperBound = 1366;
    final private int knownLowerBound = 1163;

    public InputReaderTest() {
        try {
            inputReader.read();
        } catch (IOException ex) {
            System.out.println("Couldn't read data: " + ex.getMessage());
        }
    }
    
    @Test(expected = IOException.class)
    public void testWrongInput() throws IOException {
        InputReader wrongInputReader = new InputReader("no_such_file");
        wrongInputReader.read();
    }
    
    @Test
    public void testNumberOfMachines(){
        int testNumberOfMachines = inputReader.getNumberOfMachines();
        assertEquals(numberOfMachines, testNumberOfMachines);
    }
    
    @Test
    public void testNumberOfTasks(){
        int testNumberOfTasks = inputReader.getNumberOfTasks();
        assertEquals(numberOfTasks, testNumberOfTasks);
    }
    
    @Test
    public void testKnownUpperBound(){
        int testKnownUpperBound = inputReader.getKnownUpperBound();
        assertEquals(knownUpperBound, testKnownUpperBound);
    }
    
    @Test
    public void testKnownLowerBound(){
        int testKnownLowerBound = inputReader.getKnownLowerBound();
        assertEquals(knownLowerBound, testKnownLowerBound);
    }
    
    @Test
    public void testTimesBiggerThanZero(){
        int[][] testTimes = inputReader.getTimes();
        for(int[] timesPerTask : testTimes){
            for(int time : timesPerTask){
                assertNotSame(0, time);
            }
        }
    }
    
    @Test
    public void testMachinesSmallerThanNumberOfMachines(){
        int[][] testMachines = inputReader.getMachines();
        for(int[] machinesPerTask : testMachines){
            for(int machine : machinesPerTask){
                assertTrue(machine < inputReader.getNumberOfMachines());
            }
        }
    }
    
}
