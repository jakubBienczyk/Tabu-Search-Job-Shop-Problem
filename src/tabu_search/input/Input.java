package tabu_search.input;

import java.io.IOException;

public interface Input {
    public void read() throws IOException;
    
    public int getNumberOfMachines() ;

    public int getNumberOfTasks() ;

    public int getKnownLowerBound();

    public int getKnownUpperBound() ;

    public int[][] getMachines();

    public int[][] getTimes() ;
}
