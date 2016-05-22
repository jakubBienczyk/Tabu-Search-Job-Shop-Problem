package tabu_search;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String pathsSuffix = "test/tabu_search/inputs/ta";
    private static List<String> paths;
    private static int loopsNumber = 20000;
    private static int minimumTabuListSize = 40;
    private static int maximumTabuListSize = 140;

    public static void main(String[] args) throws Exception {
        setPathList();
        for (String suff : paths) {
            for (int i = minimumTabuListSize; i < maximumTabuListSize; i++) {
                TabuSearchBuilder builder = new TaillardBuilder(suff, i);
                TabuSearch tabuSearch = new TabuSearch(builder, loopsNumber);
                System.out.println("size: " + i + ", file: " + suff);
                tabuSearch.run();
            }
        }
    }

    public static void setPathList() {
        paths = new ArrayList<>();

        for (int i = 6; i < 27; i++) {
            if (i > 9)
                paths.add(pathsSuffix + i);
            else
                paths.add(pathsSuffix + "0" + i);
        }
    }

}
