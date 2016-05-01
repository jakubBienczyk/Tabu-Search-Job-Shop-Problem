package tabu_search.heuristic;

import java.util.ArrayList;
import java.util.Collections;

public class StarterHeuristics {
    
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
    
    
    protected int[][] SPT2() {
        ArrayList<Operation> L = new ArrayList<>();
        for(int job = 0; job < n; job++){
            L.add(new Operation(n * job, input_v[job][0]));
        }
        
        
        int[][] starter = new int[m][n];
        int[] space = new int[m];
        for(int i = 0; i < m; i++){
            space[i] = 0;
        }
        
        
        while(!L.isEmpty()){
            Collections.sort(L);
            
            Operation op = L.remove(0);
            int job_number = op.job_number / n;
            int job_index = op.job_number % n;
            int machine = input_m[job_number][job_index];
            
            starter[machine][space[machine]] = job_number;
            space[machine] = space[machine] + 1;
            
            if(job_index < m - 1) {
                L.add(new Operation(op.job_number + 1, input_v[job_number][job_index + 1]));
            }
       }
                
        
        return starter;
    }
    
    public int[][] get() {
        return SPT2();
    }
}
