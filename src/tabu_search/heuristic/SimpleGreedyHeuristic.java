package tabu_search.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import tabu_search.input.Input;

public class SimpleGreedyHeuristic implements Heuristic {

    private Input input;
    private ArrayList<Operation> operationsArray;
    private int tasks, machines;
    private int[][] graph;

    public SimpleGreedyHeuristic(Input input) {
        this.input = input;
        tasks = input.getNumberOfTasks();
        machines = input.getNumberOfMachines();
    }

    @Override
    public int[][] getGraph() {
        return graph;
    }

    @Override
    public void generateSolution() {
        clearGraph();
        addFirstOperationsFromEveryTaskToOpeartionArray();
        
        while (!operationsArray.isEmpty()) {
            Collections.sort(operationsArray);
            Operation shorterOperation = operationsArray.remove(0);
            addNewOperationToGraph(shorterOperation);
            if (shorterOperation.operationIndex < machines - 1) {
                addNewOperationToOpeartionArray(shorterOperation);
            }
        }
    }

    private void addFirstOperationsFromEveryTaskToOpeartionArray() {
        operationsArray = new ArrayList<>();
        for (int task = 0; task < tasks; task++) {
            operationsArray.add(
                    new Operation(task, input.getMachines()[task][0], input.getTimes()[task][0], 0));
        }
    }
    
    private void clearGraph() {
        graph = new int[machines][tasks];
        for(int i = 0; i < machines; i++)
            for(int j = 0; j < tasks; j++)
                graph[i][j] = -1;
    }

    private void addNewOperationToGraph(Operation operation) {
        int operationIndex = 0;
        while (graph[operation.machine][operationIndex] != -1) {
            operationIndex++;
        }
        graph[operation.machine][operationIndex] = operation.task;
    }

    private void addNewOperationToOpeartionArray(Operation operation) {
        int task = operation.task;
        int index = operation.operationIndex + 1;
        operationsArray.add(new Operation(task,
                input.getMachines()[task][index],
                input.getTimes()[task][index],
                index));
    }

}
