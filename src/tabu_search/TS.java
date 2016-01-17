package tabu_search;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TS {
    private static final String invariant_suffix = "test/tabu_search/inputs/ta";
    private static final List<String> tests_suffixes = Arrays.asList(
        invariant_suffix + "01",
        invariant_suffix + "02",
        invariant_suffix + "03",
        invariant_suffix + "04",
        invariant_suffix + "05",
        invariant_suffix + "06",
        invariant_suffix + "07",
        invariant_suffix + "08",
        invariant_suffix + "09",
        invariant_suffix + "10",
        invariant_suffix + "11",
        invariant_suffix + "61"
    );
    
    public static void main(String[] args) throws Exception {
        int loopsNumber = 10000;
        //(new TabuSearch(path, loopsNumber)).run();
        for(String suff : tests_suffixes) {
            for(int i = 1; i < 100; i++){
                System.out.println("i: " + i);
              //  (new TabuList()).setMax_size(i);
                (new TabuSearch(Paths.get(System.getProperty("user.dir"), suff), loopsNumber)).run();
            }
        }
    }

}
