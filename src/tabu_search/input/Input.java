package tabu_search.input;

public interface Input {
    public int getNumberOfMachines() ;

    public int getNumberOfTasks() ;

    public int getKnownLowerBound();

    public int getKnownUpperBound() ;

    public int[][] getMachines();

    public int[][] getTimes() ;
}
