package tabu_search.input;

public class MockInput implements Input{

    @Override
    public int getNumberOfMachines() {
        return 3;
    }

    @Override
    public int getNumberOfTasks() {
        return 4;
    }

    @Override
    public int getKnownLowerBound() {
        return 1;
    }

    @Override
    public int getKnownUpperBound() {
        return 100;
    }

    @Override
    public int[][] getMachines() {
        int[][] machines = {{0,1,2},{0,1,2},{2,0,1},{1,0,2}};
        return machines;
    }

    @Override
    public int[][] getTimes() {
        int[][] machines = {{1,5,12},{2,6,11},{3,7,10},{4,8,9}};
        return machines;
    }
    
}
