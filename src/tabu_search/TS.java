package tabu_search;

public class TS {

    public static void main(String[] args) {
        String path = "";
        int loopsNumber = 10000;
        //(new TabuSearch(path, loopsNumber)).run();
        
        for(int i = 1; i < 100; i++){
            System.out.println("i: " + i);
            (new TabuList()).setMax_size(i);
            (new TabuSearch(path, loopsNumber)).run();
        }

    }

}
