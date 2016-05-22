package tabu_search.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InputReader implements Input { 

    final private Path path;
    final private String pathPrefix = "/test/tabu_search/inputs/";
    private List<String> lines;
    private int numberOfMachines, numberOfTasks, knownLowerBound, knownUpperBound;
    private int[][] times, machines;

    public InputReader(String path) {
        this.path = Paths.get(System.getProperty("user.dir"), pathPrefix + path);
    }
    
    public InputReader(Path path) {
        this.path = path;
    }

    @Override
    public void read() throws IOException {
        lines = Files.readAllLines(path);
        readFirstLine();
        readTimes();
        readMachines();
    }

    private void readFirstLine() {
        String[] firstLine = lines.get(0).split("\\s+");

        numberOfTasks = Integer.parseInt(firstLine[1]);
        numberOfMachines = Integer.parseInt(firstLine[2]);
        knownUpperBound = Integer.parseInt(firstLine[5]);
        knownLowerBound = Integer.parseInt(firstLine[6]);
    }

    private void readTimes() {
        times = new int[numberOfTasks][numberOfMachines];

        int firstDataIndex = lines.indexOf("Times") + 1;
        readTable(firstDataIndex, times);
    }

    private void readMachines() {
        machines = new int[numberOfTasks][numberOfMachines];

        int firstDataIndex = lines.indexOf("Machines") + 1;
        readTable(firstDataIndex, machines);
        decrementMachines();
    }

    private void readTable(int firstDataIndex, int[][] table) {
        for (int task = 0; task < numberOfTasks; task++) {
            String[] machinesPerTask = lines.get(task + firstDataIndex).trim().split("\\s+");
            for (int operation = 0; operation < numberOfMachines; operation++) {
                table[task][operation] = Integer.valueOf(machinesPerTask[operation]);
            }
        }
    }
    
    private void decrementMachines() {
        for (int task = 0; task < numberOfTasks; task++) {
            for (int operation = 0; operation < numberOfMachines; operation++) {
                machines[task][operation] = machines[task][operation] - 1;
            }
        }
    }

    @Override
    public int getNumberOfMachines() {
        return numberOfMachines;
    }

    @Override
    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    @Override
    public int getKnownLowerBound() {
        return knownLowerBound;
    }

    @Override
    public int getKnownUpperBound() {
        return knownUpperBound;
    }

    @Override
    public int[][] getMachines() {
        return machines;
    }

    @Override
    public int[][] getTimes() {
        return times;
    }

}
