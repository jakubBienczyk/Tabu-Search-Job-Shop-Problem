package tabu_search.graph;

import tabu_search.input.Input;

public class GraphChanger {

    private Input input;
    private OperationVertex[] firstOperationsOnMachines;
    private int[][] graphTable;

    public GraphChanger(Input input) {
        this.input = input;
    }

    public Graph fromTable(int[][] graphTable) {
        this.graphTable = graphTable;
        firstOperationsOnMachines = new OperationVertex[input.getNumberOfMachines()];
        setFirstOperationsOnMachines();
        setRestOperationsOnMachines();
        addEdgesFromOperationsOrderOnTasks();
        return new Graph(firstOperationsOnMachines);
    }

    private void setFirstOperationsOnMachines() {
        for (int machine = 0; machine < input.getNumberOfMachines(); machine++) {
            int task = graphTable[machine][0];
            int time = getOpeartionTime(task, machine);
            firstOperationsOnMachines[machine] = new OperationVertex(task, machine, time);
        }
    }

    private void setRestOperationsOnMachines() {
        for (int machine = 0; machine < input.getNumberOfMachines(); machine++) {
            OperationVertex operation = firstOperationsOnMachines[machine];
            for (int task = 1; task < input.getNumberOfTasks(); task++) {
                int nextOperationTask = graphTable[machine][task];
                int nextOperationTime = getOpeartionTime(nextOperationTask, machine);
                OperationVertex nextOperation = new OperationVertex(nextOperationTask, machine, nextOperationTime);
                operation.setNextOnMachine(nextOperation);
                nextOperation.setPreviousOnMachine(operation);
                operation = nextOperation;
            }
        }
    }
    
    private void addEdgesFromOperationsOrderOnTasks() {
        for(int task = 0; task < input.getNumberOfTasks(); task++) {
            OperationVertex operation = findOperation(input.getMachines()[task][0], task);
            for(int machine = 1; machine < input.getNumberOfMachines(); machine++) {
                OperationVertex nextOperation = findOperation(input.getMachines()[task][machine], task);
                operation.setNextOnTask(nextOperation);
                nextOperation.setPreviousOnTask(operation);
                operation = nextOperation;
            }
        }
    }
    
    private OperationVertex findOperation(int machine, int task) {
        OperationVertex operation = firstOperationsOnMachines[machine];
        while(operation.getTask() != task) {
            operation = operation.getNextOnMachine();
        }
        return operation;
    }

    private int getOpeartionTime(int task, int machine) {
        for (int machineOnTask = 0; machineOnTask < input.getNumberOfMachines(); machineOnTask++) {
            if (input.getMachines()[task][machineOnTask] == machine) {
                return input.getTimes()[task][machineOnTask];
            }
        }
        return 0;
    }
}
