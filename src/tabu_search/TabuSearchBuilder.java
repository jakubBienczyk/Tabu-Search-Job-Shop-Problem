package tabu_search;

import java.io.PrintStream;
import tabu_search.graph.Graph;
import tabu_search.input.Input;
import tabu_search.longestPath.LongestPath;
import tabu_search.neighbors.NeighborsGenerator;

public interface TabuSearchBuilder {

    public Input getInput();

    public Graph getGraph();

    public LongestPath getLongestPath();

    public NeighborsGenerator getNeighborsGenerator();

    public TabuList getTabuList();
    
    public PrintStream getStream();
}
