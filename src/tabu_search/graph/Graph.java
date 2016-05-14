package tabu_search.graph;

public class Graph {

    private OperationVertex[] firstOperationsOnMachines;

    public Graph(OperationVertex[] firstOperationsOnMachines) {
        this.firstOperationsOnMachines = firstOperationsOnMachines;
    }

    public OperationVertex[] getFirstOperationsOnMachines() {
        return firstOperationsOnMachines;
    }

    public void setFirstOperationsOnMachines(OperationVertex[] firstOperationsOnMachines) {
        this.firstOperationsOnMachines = firstOperationsOnMachines;
    }

    @Override
    public Graph clone() {
        OperationVertex[] firstOperationsInNewGraph = new OperationVertex[firstOperationsOnMachines.length];
        for (int machine = 0; machine < firstOperationsOnMachines.length; machine++) {
            firstOperationsInNewGraph[machine] = firstOperationsOnMachines[machine].clone(null, null);
        }
        setPreviousOperations(firstOperationsInNewGraph);
        return new Graph(firstOperationsInNewGraph);
    }

    private void setPreviousOperations(OperationVertex[] firstOperationsInNewGraph) {
        for (int machine = 0; machine < firstOperationsOnMachines.length; machine++) {
            OperationVertex previousOperationOnTask = firstOperationsOnMachines[machine].getPreviousOnTask();
            if (previousOperationOnTask != null) {
                OperationVertex previousOperationOnTaskInNewGraph = firstOperationsInNewGraph[previousOperationOnTask.getMachine()];
                while (!previousOperationOnTaskInNewGraph.equals(previousOperationOnTask)) {
                    previousOperationOnTaskInNewGraph = previousOperationOnTaskInNewGraph.getNextOnMachine();
                }

                firstOperationsInNewGraph[machine].setPreviousOnTask(previousOperationOnTaskInNewGraph);
            }
        }
    }

}
