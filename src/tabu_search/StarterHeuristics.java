/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabu_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author ja
 */
public class StarterHeuristics {
    
    protected class Operation implements Comparable<Operation> {
        public int job_number;
        public int time;
        
        public Operation(int job_number, int time) {
            this.job_number = job_number;
            this.time = time;
        }

        @Override
        public int compareTo(Operation o) {
            return time - o.time;
        }
    }
    
    private int n;
    private int m;
    private int[][] input_v;
    private int[][] input_m;
    
    public StarterHeuristics(int n, int m, int[][] input_v, int[][] input_m) {
        this.n = n;
        this.m = m;
        this.input_v = input_v.clone();
        this.input_m = input_m.clone();
    }
    
    protected int[][] SPT() {
        System.out.println("m = " + m + ", n = " + n);
        // Shortest Processing Time First heuristic
        ArrayList<ArrayList<Operation>> vsrtd = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            vsrtd.add(new ArrayList<>());
            for(int j = 0; j < n; j++) {
                vsrtd.get(i).add(null);
            }
        }
        
        for(int j = 0; j < n; j++) { // j = nr zadania
            for(int op = 0; op < m; op++) {
                vsrtd.get(input_m[j][op]).set(j, new Operation(j, input_v[j][op]));
            }
        }
        
        for(int i = 0; i < m; i++) {
            Collections.sort(vsrtd.get(i));
        }
        
        int[][] res = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                res[i][j] = vsrtd.get(i).get(j).job_number;
            }
        }
        System.out.println("starter: " + Arrays.deepToString(res));
        return res;
    }
    
    public int[][] get() {
        return SPT();
        /*return new int[][] {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}};
        */
    }
}
