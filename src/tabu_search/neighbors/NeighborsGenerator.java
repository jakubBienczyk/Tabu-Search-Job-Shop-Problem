package tabu_search.neighbors;

import java.util.List;
import tabu_search.graph.Graph;

public interface NeighborsGenerator {
    public void setGraph(Graph graph);
    public void setLongestPath(List path);
    public void calculate();
    public List<Neighbor> getNeighbors();
}
