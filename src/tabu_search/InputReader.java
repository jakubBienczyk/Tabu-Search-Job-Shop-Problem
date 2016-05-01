package tabu_search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InputReader {

    final private Path path;
    final private String pathPrefix = "/test/tabu_search/inputs/";
    private List<String> lines;
    private int numberOfMachines, numberOfTasks, knownLowerBound, knownUpperBound;
    private int[][] times, machines;

    public InputReader(String path) {
        this.path = Paths.get(System.getProperty("user.dir"), pathPrefix + path);
    }

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
    }

    private void readTable(int firstDataIndex, int[][] table) {
        for (int task = 0; task < numberOfTasks; task++) {
            String[] machinesPerTask = lines.get(task + firstDataIndex).trim().split("\\s+");
            for (int operation = 0; operation < numberOfMachines; operation++) {
                table[task][operation] = Integer.valueOf(machinesPerTask[operation]);
            }
        }
    }

    public int getNumberOfMachines() {
        return numberOfMachines;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public int getKnownLowerBound() {
        return knownLowerBound;
    }

    public int getKnownUpperBound() {
        return knownUpperBound;
    }

    public int[][] getMachines() {
        return machines;
    }

    public int[][] getTimes() {
        return times;
    }

}
