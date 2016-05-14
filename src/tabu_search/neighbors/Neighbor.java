package tabu_search.neighbors;

import tabu_search.graph.Graph;

public class Neighbor {

    private final Graph graph;
    private final int firstVertexIndexOnLongestPath, secondVertexIndexOnLongestPath;

    public Neighbor(Graph graph, int firstVertexIndexOnLongestPath, int secondVertexIndexOnLongestPath) {
        this.graph = graph;
        this.firstVertexIndexOnLongestPath = firstVertexIndexOnLongestPath;
        this.secondVertexIndexOnLongestPath = secondVertexIndexOnLongestPath;
    }

    public Graph getGraph() {
        return graph;
    }

    public int getFirstVertexIndexOnLongestPath() {
        return firstVertexIndexOnLongestPath;
    }

    public int getSecondVertexIndexOnLongestPath() {
        return secondVertexIndexOnLongestPath;
    }

}
