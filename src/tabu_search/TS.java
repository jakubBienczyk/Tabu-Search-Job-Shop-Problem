package tabu_search;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TS {
    private static final String invariant_suffix = "test/tabu_search/inputs/ta";
    private static List<String> tests_suffixes;
    //= Arrays.asList(
//        invariant_suffix + "01",
//        invariant_suffix + "02",
//        invariant_suffix + "03",
//        invariant_suffix + "04",
//        invariant_suffix + "05",
//        invariant_suffix + "06",
//        invariant_suffix + "07",
//        invariant_suffix + "08",
//        invariant_suffix + "09",
//        invariant_suffix + "10",
      //  invariant_suffix + "11",
       // invariant_suffix + "61"
    //);
    
    public static void main(String[] args) throws Exception {
        int loopsNumber = 2000000;
        tests_suffixes = new ArrayList<>();
        for(int i = 26; i < 27; i++){
            if(i > 9){
                tests_suffixes.add(invariant_suffix + i);
            } else {
                tests_suffixes.add(invariant_suffix + "0" + i);
            }
        }
        //(new TabuSearch(path, loopsNumber)).run();
        for(String suff : tests_suffixes) {
            for(int i = 50; i < 130; i = i + 3){
                System.out.print("size: " + i + ", file: " + suff);
                (new TabuList()).setMax_size(i);
                (new TabuSearch(Paths.get(System.getProperty("user.dir"), suff), loopsNumber)).run();
            }
        }
    }

}
