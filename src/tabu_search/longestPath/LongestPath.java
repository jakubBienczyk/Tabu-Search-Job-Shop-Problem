package tabu_search.longestPath;

import java.util.List;
import tabu_search.graph.Graph;

public interface LongestPath {
    public void calculate();
    public void setGraph(Graph graph);
    public int getPathLength();
    public List getPath();
}
