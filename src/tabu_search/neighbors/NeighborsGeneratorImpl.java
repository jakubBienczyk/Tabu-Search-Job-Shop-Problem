package tabu_search.neighbors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tabu_search.graph.Graph;
import tabu_search.graph.OperationVertex;

public class NeighborsGeneratorImpl implements NeighborsGenerator {

    private Graph graph;
    private List longestPath;
    private List<Neighbor> neighbors;
    private Iterator longestPathIterator;
    private OperationVertex operationOnPath;
    private List<OperationVertex> operationsOnOneMachine;

    public NeighborsGeneratorImpl(Graph graph) {
        this(graph, null);
    }

    public NeighborsGeneratorImpl(Graph graph, List longestPath) {
        this.graph = graph;
        this.longestPath = longestPath;
    }

    @Override
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void setLongestPath(List path) {
        this.longestPath = path;
    }

    @Override
    public List getNeighbors() {
        return neighbors;
    }

    @Override
    public void calculate() {
        neighbors = new ArrayList<>();
        longestPathIterator = longestPath.iterator();
        operationOnPath = (OperationVertex) longestPathIterator.next();

        while (longestPathIterator.hasNext()) {
            int machine = operationOnPath.getMachine();
            getOperationsOnMachine(machine);

            if (operationsOnOneMachine.size() > 1) {
                addNeighborhoodFromTheBeginning();
                addNeighborhoodFromTheEnd();
            }
        }
    }

    private void getOperationsOnMachine(int machine) {
        operationsOnOneMachine = new ArrayList<>();
        while (longestPathIterator.hasNext()) {
            if (operationOnPath.getMachine() == machine)
                operationsOnOneMachine.add(operationOnPath);
            else
                break;
            operationOnPath = (OperationVertex) longestPathIterator.next();
        }
    }

    private void addNeighborhoodFromTheBeginning() {
        OperationVertex firstOperationInBlock = operationsOnOneMachine.get(0);
        int indexOfFirstOperationInBlock = longestPath.indexOf(firstOperationInBlock);
        if (indexOfFirstOperationInBlock != 0)
            addNeighborhoodWithReplaced(firstOperationInBlock, firstOperationInBlock.getNextOnMachine());
    }

    private void addNeighborhoodFromTheEnd() {
        OperationVertex lastOperationInBlock = operationsOnOneMachine.get(operationsOnOneMachine.size() - 1);
        int indexOfLastOperationInBlock = longestPath.indexOf(lastOperationInBlock);
        if (indexOfLastOperationInBlock != longestPath.size() - 1)
            addNeighborhoodWithReplaced(lastOperationInBlock, lastOperationInBlock.getPreviousOnMachine());
    }

    private void addNeighborhoodWithReplaced(OperationVertex op1, OperationVertex op2) {
        Graph newGraph = graph.clone();
        OperationVertex[] firstOperationsInNewGraph = newGraph.getFirstOperationsOnMachines();
        replaceOperations(firstOperationsInNewGraph[op1.getMachine()], op1, op2);

        int indexOfOp1 = longestPath.indexOf(op1);
        int indexOfOp2 = longestPath.indexOf(op2);
        neighbors.add(new Neighbor(newGraph, indexOfOp1, indexOfOp2));
    }

    private void replaceOperations(OperationVertex firstOperation, OperationVertex op1, OperationVertex op2) {
        OperationVertex op1InGraph = getOperationFromGraph(firstOperation, op1);
        OperationVertex op2InGraph = getOperationFromGraph(firstOperation, op2);
        op1InGraph.setNextOnMachine(op2InGraph.getNextOnMachine());
        op2InGraph.setNextOnMachine(op1InGraph);
        op2InGraph.setPreviousOnMachine(op1InGraph.getPreviousOnMachine());
        op1InGraph.setPreviousOnMachine(op2InGraph);
    }

    private OperationVertex getOperationFromGraph(OperationVertex firstOperationInGraph, OperationVertex op) {
        OperationVertex opInGraph = firstOperationInGraph;
        while (!opInGraph.equals(op)) {
            opInGraph = opInGraph.getNextOnMachine();
        }
        return opInGraph;
    }

}
