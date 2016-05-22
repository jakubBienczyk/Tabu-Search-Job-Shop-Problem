package tabu_search;

import java.io.PrintStream;
import tabu_search.graph.Graph;
import tabu_search.input.Input;
import tabu_search.longestPath.LongestPath;
import tabu_search.neighbors.Neighbor;
import tabu_search.neighbors.NeighborsGenerator;

public class TabuSearch {

    private final Input input;
    private Graph graph, bestSolution;
    private LongestPath longestPath;
    private final int loopsNumber;
    private final NeighborsGenerator generator;
    private Neighbor bestNeighbor;
    private boolean isBestNeighborFromTabu;
    private int positionOnTabuList, bestNeighborPath;
    private final TabuList tabuList;
    private int bestSolutionPath;
    private PrintStream printStream;
    
    public TabuSearch(TabuSearchBuilder builder, int loopsNumber) {
        this.input = builder.getInput();
        this.graph = builder.getGraph();
        bestSolution = graph;
        this.longestPath = builder.getLongestPath();
        this.loopsNumber = loopsNumber;
        this.generator = builder.getNeighborsGenerator();
        this.tabuList = builder.getTabuList();
        this.printStream = builder.getStream();
    }

    public void run() {
        tabuList.clear();
        bestSolutionPath = 0;
        for (int loop = 0; loop < loopsNumber; loop++) {
            generateNeighbors();
            adjustVariables();
            generator.getNeighbors().stream().forEach((neighbor) -> checkNeighbor(neighbor));
            graph = bestNeighbor.getGraph();
            saveBestNeighbor();
            manageTabuList();
        }
        printScore(bestSolutionPath);
    }

    private void adjustVariables() {
        bestNeighbor = null;
        bestNeighborPath = 0;
        positionOnTabuList = -1;
        isBestNeighborFromTabu = false;
    }

    private void generateNeighbors() {
        longestPath.setGraph(graph);
        longestPath.calculate();
        generator.setGraph(graph);
        generator.setLongestPath(longestPath.getPath());
        generator.calculate();
    }

    private void checkNeighbor(Neighbor neighbor) {
        longestPath.setGraph(neighbor.getGraph());
        longestPath.calculate();
        int pathLength = longestPath.getPathLength();
        boolean isNeighborFromTabu = tabuList.contains(neighbor.getFirstVertexIndexOnLongestPath(), neighbor.getSecondVertexIndexOnLongestPath());
        if (bestNeighbor == null)
            firstNeighbor(neighbor, pathLength);
        else if (bestSolutionPath > pathLength)
            bestNeighbor(neighbor, pathLength);
        else if (isBestNeighborFromTabu && isNeighborFromTabu)
            neighborFromTabu(neighbor, pathLength);
        else if ((bestNeighborPath >= pathLength || isBestNeighborFromTabu) && !isNeighborFromTabu)
            neighborNotFromTabu(neighbor, pathLength);
    }

    private void saveBestNeighbor() {
        if (bestSolutionPath == 0 || bestSolutionPath > bestNeighborPath) {
            bestSolutionPath = bestNeighborPath;
            bestSolution = graph;
        }
    }

    private void manageTabuList() {
        if (!isBestNeighborFromTabu && positionOnTabuList >= 0)
            tabuList.remove(positionOnTabuList);
        tabuList.add(bestNeighbor.getFirstVertexIndexOnLongestPath(), bestNeighbor.getSecondVertexIndexOnLongestPath());
    }

    private void firstNeighbor(Neighbor neighbor, int pathLength) {
        bestNeighbor = neighbor;
        bestNeighborPath = pathLength;
        isBestNeighborFromTabu = tabuList.contains(neighbor.getFirstVertexIndexOnLongestPath(), neighbor.getSecondVertexIndexOnLongestPath());
        if (isBestNeighborFromTabu)
            positionOnTabuList = tabuList.position(neighbor.getFirstVertexIndexOnLongestPath(), neighbor.getSecondVertexIndexOnLongestPath());
    }

    private void bestNeighbor(Neighbor neighbor, int pathLength) {
        isBestNeighborFromTabu = false;
        bestNeighbor = neighbor;
        bestNeighborPath = pathLength;
    }

    private void neighborFromTabu(Neighbor neighbor, int pathLength) {
        int neighborPosition = tabuList.position(
                neighbor.getFirstVertexIndexOnLongestPath(),
                neighbor.getSecondVertexIndexOnLongestPath());
        if (!isPostionOnTabuListSmaller(neighborPosition))
            return;
        isBestNeighborFromTabu = true;
        positionOnTabuList = neighborPosition;
        bestNeighbor = neighbor;
        bestNeighborPath = pathLength;
    }

    private void neighborNotFromTabu(Neighbor neighbor, int pathLength) {
        isBestNeighborFromTabu = false;
        bestNeighbor = neighbor;
        bestNeighborPath = pathLength;
    }

    private boolean isPostionOnTabuListSmaller(int neighborPosition) {
        return neighborPosition <= positionOnTabuList || positionOnTabuList < 0;
    }

    private void printScore(int bestSolutionPath) {
        printStream.println(" ub = " + input.getKnownUpperBound()
                + " max = " + bestSolutionPath
                + " ratio = " + (bestSolutionPath * 1.0 / input.getKnownUpperBound()));
    }

}
