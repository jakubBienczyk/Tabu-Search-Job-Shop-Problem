package tabu_search.longestPath;

import java.util.ArrayList;
import java.util.List;
import tabu_search.graph.Graph;
import tabu_search.graph.OperationVertex;

public class LongestPathImpl implements LongestPath {

    private Graph graph;
    private int machines, tasks;
    private Integer longestPathLength;
    private List<OperationVertex> longestPath;
    private int[][] longestPathToOperationsOnMachines;

    public LongestPathImpl(int numberOfMachines, int numberOfTasks) {
        this(null, numberOfMachines, numberOfTasks);
    }

    public LongestPathImpl(Graph graph, int numberOfMachines, int numberOfTasks) {
        this.graph = graph;
        machines = numberOfMachines;
        tasks = numberOfTasks;
        longestPathToOperationsOnMachines = new int[machines][tasks];
    }

    @Override
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public int getPathLength() {
        if(longestPathLength == null) {
            throw new NullPointerException("Path is not calculated!");
        }
        return longestPathLength;
    }

    @Override
    public List getPath() {
        if(longestPath == null) {
            throw new NullPointerException("Path is not calculated!");
        }
        return longestPath;
    }

    @Override
    public void calculate() {
        if(graph == null) {
            throw new NullPointerException("Graph is not included!");
        }
        clearAuxiliaryArray();
        for (int machine = 0; machine < machines; machine++) {
            OperationVertex lastOperation = findLastOperationOnMachine(machine);
            calculateLongestPathFromOperation(lastOperation);
        }
        setLongerPath();
    }

    private void clearAuxiliaryArray() {
        for (int machine = 0; machine < machines; machine++) {
            for (int task = 0; task < tasks; task++) {
                longestPathToOperationsOnMachines[machine][task] = -1;
            }
        }

    }

    private OperationVertex findLastOperationOnMachine(int machine) {
        OperationVertex operation = graph.getFirstOperationsOnMachines()[machine];
        while (operation.getNextOnMachine() != null) {
            operation = operation.getNextOnMachine();
        }
        return operation;
    }

    private void calculateLongestPathFromOperation(OperationVertex operation) {
        OperationVertex previousOnMachine = operation.getPreviousOnMachine();
        OperationVertex previousOnTask = operation.getPreviousOnTask();

        int pathToOperationOnPreviousMachine = getPathLengthFromOperation(previousOnMachine);
        if (pathToOperationOnPreviousMachine < 0) {
            calculateLongestPathFromOperation(previousOnMachine);
        }
        pathToOperationOnPreviousMachine = getPathLengthFromOperation(previousOnMachine);

        int pathToOperationOnPreviousTask = getPathLengthFromOperation(previousOnTask);
        if (pathToOperationOnPreviousTask < 0) {
            calculateLongestPathFromOperation(previousOnTask);
        }
        pathToOperationOnPreviousTask = getPathLengthFromOperation(previousOnTask);

        int longerPath = Math.max(pathToOperationOnPreviousMachine, pathToOperationOnPreviousTask);
        longestPathToOperationsOnMachines[operation.getMachine()][operation.getTask()] = longerPath + operation.getTime();
    }

    private void setLongerPath() {
        longestPath = new ArrayList();
        OperationVertex operation = getOperationWithLongerPathAndSetLongerPathLength();
        setLongerPathFromOperation(operation);
    }

    private OperationVertex getOperationWithLongerPathAndSetLongerPathLength() {
        OperationVertex operationVertex = null;
        longestPathLength = -1;

        for (int machine = 0; machine < machines; machine++) {
            int longestPathLengthOnMachine = longestPathToOperationsOnMachines[machine][tasks - 1];
            if (longestPathLengthOnMachine > longestPathLength) {
                longestPathLength = longestPathLengthOnMachine;
                operationVertex = findLastOperationOnMachine(machine);
            }
        }

        return operationVertex;
    }
    
    private void setLongerPathFromOperation(OperationVertex operation){
        longestPath.add(0, operation);
        while (operation.getPreviousOnMachine() != null || operation.getPreviousOnTask() != null) {
            OperationVertex previousOnMachine = operation.getPreviousOnMachine();
            OperationVertex previousOnTask = operation.getPreviousOnTask();
            int pathToOperationOnPreviousMachine = getPathLengthFromOperation(previousOnMachine);
            int pathToOperationOnPreviousTask = getPathLengthFromOperation(previousOnTask);
            if(pathToOperationOnPreviousMachine > pathToOperationOnPreviousTask) {
                longestPath.add(0, previousOnMachine);
                operation = previousOnMachine;
            } else {
                longestPath.add(0, previousOnTask);
                operation = previousOnTask;
            }
        }
    }

    private int getPathLengthFromOperation(OperationVertex operation) {
        if (operation == null) {
            return 0;
        }
        return longestPathToOperationsOnMachines[operation.getMachine()][operation.getTask()];
    }

}
