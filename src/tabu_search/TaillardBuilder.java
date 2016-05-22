package tabu_search;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import tabu_search.graph.Graph;
import tabu_search.graph.GraphChanger;
import tabu_search.heuristic.SimpleGreedyHeuristic;
import tabu_search.input.Input;
import tabu_search.input.InputReader;
import tabu_search.longestPath.LongestPath;
import tabu_search.longestPath.LongestPathImpl;
import tabu_search.neighbors.NeighborsGenerator;
import tabu_search.neighbors.NeighborsGeneratorImpl;

public class TaillardBuilder implements TabuSearchBuilder {

    private Input input;
    private TabuList tabuList;

    public TaillardBuilder(String path, int tabuListSize) throws IOException {
        tabuList = new TabuList(tabuListSize);
        input = new InputReader(Paths.get(System.getProperty("user.dir"), path));
        input.read();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public Graph getGraph() {
        SimpleGreedyHeuristic heuristic = new SimpleGreedyHeuristic(input);
        heuristic.generateSolution();
        return (new GraphChanger(input)).fromTable(heuristic.getGraph());
    }

    @Override
    public LongestPath getLongestPath() {
        return new LongestPathImpl(input.getNumberOfMachines(), input.getNumberOfTasks());
    }

    @Override
    public NeighborsGenerator getNeighborsGenerator() {
        return new NeighborsGeneratorImpl();
    }

    @Override
    public TabuList getTabuList() {
        return tabuList;
    }

    @Override
    public PrintStream getStream() {
        return System.out;
    }

}
