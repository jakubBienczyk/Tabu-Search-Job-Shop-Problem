package tabu_search.graph;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    private OperationVertex[] firstOperationsOnMachines;
    private Map<OperationVertex, OperationVertex> clonedVertices;

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
        adjustClonedVertices();
        OperationVertex[] firstOperationsInClonedGraph = setFirstOperationsInClonedGraph();
        setNextOperationsOnTasks();
        return new Graph(firstOperationsInClonedGraph);
    }
    
    private void adjustClonedVertices() {
        clonedVertices = new HashMap<>();
        clonedVertices.put(null, null);
    }
    
    private OperationVertex[] setFirstOperationsInClonedGraph() {
        OperationVertex[] firstOperationsInClonedGraph = new OperationVertex[firstOperationsOnMachines.length];
        for (int machine = 0; machine < firstOperationsOnMachines.length; machine++) {
            OperationVertex orginal = firstOperationsOnMachines[machine];
            OperationVertex clonedOperation = orginal.clone();
            firstOperationsInClonedGraph[machine] = clonedOperation;
            clonedVertices.put(firstOperationsOnMachines[machine], clonedOperation);
            addNextOperationsThroughMachinesOrder(clonedOperation, orginal);
        }
        return firstOperationsInClonedGraph;
    }
    
    private void addNextOperationsThroughMachinesOrder(OperationVertex toCopy, OperationVertex orginal) {
        while(orginal.getNextOnMachine() != null){
            orginal = orginal.getNextOnMachine();
            OperationVertex nextOnMachineToCopy = orginal.clone();
            toCopy.setNextOnMachine(nextOnMachineToCopy);
            nextOnMachineToCopy.setPreviousOnMachine(toCopy);
            toCopy = nextOnMachineToCopy;
            clonedVertices.put(orginal, toCopy);
        }
    }

    private void setNextOperationsOnTasks() {
        clonedVertices.keySet().stream().filter(elem -> elem != null).forEach((orginal) -> {
            OperationVertex toClone = clonedVertices.get(orginal);
            toClone.setNextOnTask(clonedVertices.get(orginal.getNextOnTask()));
            toClone.setPreviousOnTask(clonedVertices.get(orginal.getPreviousOnTask()));
        });
    }

}
