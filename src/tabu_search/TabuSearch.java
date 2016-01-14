/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabu_search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jakub
 */
public class TabuSearch {

    private int[][] pi; // [m][n] - początkowe pi (wynik)
    private int[][] best_pi;
    private final InputManager input;
    private final int loopsNumber;

    public TabuSearch(String path, int loopsNumber) {
        input = new InputManager(path);
        pi = input.getStarter();
        this.loopsNumber = loopsNumber;
    }

    public void run() {
        int loops = loopsNumber;
        
        LongestPath.setInput_m(input.getInput_m());
        LongestPath.setInput_v(input.getInput_v());
        LongestPath.setM(input.getM());
        LongestPath.setN(input.getN());
        
        LongestPath longestPath = new LongestPath(pi);
        int max = longestPath.calculate();
        System.out.println("Max path = " + max);
        ArrayList<Integer> pathList = longestPath.getLongest_path();
        
        best_pi = pi;

        while (loops > 0) {
            System.out.println("Loop: " + loops);
            loops--;
            
            NeighborsGenerator generator = new NeighborsGenerator(pi, null, input, pathList);
            
            int neigh_max = -1;
            while(generator.hasNext()){
                System.out.println("tutaj");
                int[][] next = generator.next();
                System.out.println("Next: " + Arrays.deepToString(next));
                longestPath = new LongestPath(next);
                int path = longestPath.calculate();
                System.out.println("Path = " + path);
                if(neigh_max < 0 || path <= neigh_max){
                    neigh_max = path;
                    pi = next;
                    pathList = longestPath.getLongest_path();
                }
            }
            
            System.out.println("Max path = " + max);
            
            if(neigh_max < 0){
                break;
            }
            
            if(neigh_max < max){
                max = neigh_max;
                best_pi = pi;
            }
        }
        
        System.out.println("max = " + max);
        System.out.println(Arrays.deepToString(best_pi));

    }
}
