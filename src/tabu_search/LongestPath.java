package tabu_search;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LongestPath {

    private static int n, m; // n - liczba zadań, m -liczba maszyn
    private static int[][] input_m; // [n][m] - maszyny odpowiadające kolejnym operacjom, dla każdego zadania
    private static int[][] input_v; // [n][m] - czas wykoniania każdej operacji

    //dane do przeliczenia
    private final int[][] pi; //graf 
    private final int[][] lengths; //graf 
    private final ArrayList<Integer>[][] paths; //graf 
    private ArrayList<Integer> longest_path;

    public LongestPath(int[][] pi) {
        this.pi = pi;
        lengths = new int[m][n];
        paths = new ArrayList[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                lengths[i][j] = -1;
                paths[i][j] = new ArrayList<>();
            }
        }
    }

    public int calculate() {
        int i = 0;
        int max = -1;
        while (i < m) {
            calculate(i, n - 1);
            if (lengths[i][n - 1] > max || max < 0) {
                max = lengths[i][n - 1];
                longest_path = paths[i][n - 1];
            }
            i++;
        }
        return max;
    }

    public void calculate(int m1, int n1) {

        int max = 0;
        ArrayList<Integer> path = new ArrayList<>();

        // bierzemy poprzedzającą operację na maszynie m1
        if (n1 > 0) {
            if (lengths[m1][n1 - 1] < 0) {
                calculate(m1, n1 - 1);
            }
            path = (ArrayList<Integer>) paths[m1][n1 - 1].clone();
            max = lengths[m1][n1 - 1];
        }

        // bierzemy poprzedzającą operację dla zadania pi[m1][n1]
        int task_number = pi[m1][n1]; // numer zadania
        int operation_index = getOperationIndex(m1, task_number); //indeks operacji dla danego zadania
        if (operation_index > 0) {
            int prev_machine = input_m[task_number][operation_index - 1]; //maszyna, na której jest wykonywana poprzednia operacja
            int task_index_on_machine = getTaskIndex(prev_machine, task_number); //indeks zadania na tej maszynie
            if (lengths[prev_machine][task_index_on_machine] < 0) {
                calculate(prev_machine, task_index_on_machine);
            }
            if (lengths[prev_machine][task_index_on_machine] > max) {
                max = lengths[prev_machine][task_index_on_machine];
                path = (ArrayList<Integer>) paths[prev_machine][task_index_on_machine].clone();
            }
        }

        lengths[m1][n1] = max + input_v[task_number][operation_index];
        path.add(m1 * n + n1);
        paths[m1][n1] = path;
    }

    private int getTaskIndex(int m1, int n1) {
        int task_index = -1;
        for (int i = 0; i < n; i++) {
            if (pi[m1][i] == n1) {
                task_index = i;
                break;
            }
        }
        return task_index;
    }

    private int getOperationIndex(int m1, int n1) {
        int operation_index = -1;
        for (int i = 0; i < m; i++) {
            if (input_m[n1][i] == m1) {
                operation_index = i;
                break;
            }
        }
        return operation_index;
    }

    public static void setInput_m(int[][] input_m) {
        LongestPath.input_m = input_m;
    }

    public static void setInput_v(int[][] input_v) {
        LongestPath.input_v = input_v;
    }

    public static void setN(int n) {
        LongestPath.n = n;
    }

    public static void setM(int m) {
        LongestPath.m = m;
    }

    public ArrayList<Integer> getLongest_path() {
        return longest_path;
    }

}
