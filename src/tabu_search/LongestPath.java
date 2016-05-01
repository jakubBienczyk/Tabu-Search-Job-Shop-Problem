package tabu_search;

import java.util.ArrayList;

public class LongestPath {

    private static int n, m; // n - liczba zadań, m -liczba maszyn
    private static int[][] input_m; // [n][m] - maszyny odpowiadające kolejnym operacjom, dla każdego zadania
    private static int[][] input_v; // [n][m] - czas wykoniania każdej operacji

    //dane do przeliczenia
    private final int[][] pi; //graf, na którym będą wykonywane operacje 
    private final int[][] lengths; // długość najdłuższej ścieżki dla każdego punkt z pi
    private final ArrayList<Integer>[][] paths; // najdłuższa ścieżka dla każdego punkt z pi
    private ArrayList<Integer> longest_path; // najdłuższa ścieżka - operacje na niej są indeksowane tak, jak w tej pracy (czyli od 0 do n*m - 1)

    public LongestPath(int[][] pi) {
        this.pi = pi;

        lengths = new int[m][n];
        paths = new ArrayList[m][n];

        // ustaw wartości początkowe dla lenghts i path
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                lengths[i][j] = -1;
                paths[i][j] = new ArrayList<>();
            }
        }
    }

    public int calculate() {
        int max = -1; //początkowa najdłuższa wartość
        
        for (int i = 0; i < m; i++) { // dla każdej ostatniej operacji, na każdej maszynie obliczam 
                                      // najdłuższą ścieżkę. potem wybieram najlepszą z nich
            calculate(i, n - 1); // policz ścieżkę dla ostatniej operacji dla maszyny i-tej
            if (lengths[i][n - 1] > max || max < 0) { // jeżeli ta ścieżka jest najlepsza to zapisz
                max = lengths[i][n - 1];
                longest_path = paths[i][n - 1];
            }
        }

        return max; // zwracamy tylko długość ścieżki, ale tablicę z jej kolejnymi elementami można pobrać z geta
    }

    public void calculate(int m1, int n1) { 
        //System.out.println("calc: m1= " + m1 + ", n1= " + n1);
        // każdy z wierzchołków (odpowiadający operacji) może mieć max 2 połączenia 
        // 1) do operacji z tego samego zadania, wykonywanej przed nim
        // 2) do operacji z tej samej maszyny, wykonywanej przed nim
        
        // ustaw wartość początkowe
        int max = 0; 
        ArrayList<Integer> path = new ArrayList<>();

        // bierzemy poprzedzającą operację na maszynie m1, o ile nie jest to pierwsza operacja
        if (n1 > 0) {
            if (lengths[m1][n1 - 1] < 0) {

                calculate(m1, n1 - 1);
            }
            path = (ArrayList<Integer>) paths[m1][n1 - 1].clone();
            max = lengths[m1][n1 - 1];
        }

        // ta część algotymu jest słabo zrobiona, jak będziemy mieli czas to można ją poprawić
        // bierzemy poprzedzającą operację dla zadania pi[m1][n1], o ile nie jest to pierwsza operacja
        int task_number = pi[m1][n1]; // numer zadania
        int operation_index = getOperationIndex(m1, task_number); //indeks operacji dla danego zadania
        if (operation_index > 0) { // jeżeli nie jest to piersza operacja to weźmy dane z poprzedzającej
            int prev_machine = input_m[task_number][operation_index - 1]; //maszyna, na której jest wykonywana poprzednia operacja
            int task_index_on_machine = getTaskIndex(prev_machine, task_number); //indeks zadania na tej maszynie
            if (lengths[prev_machine][task_index_on_machine] < 0) { 
                calculate(prev_machine, task_index_on_machine); // BUG?! infinite loop
            }
            if (lengths[prev_machine][task_index_on_machine] > max) {
                max = lengths[prev_machine][task_index_on_machine];
                path = (ArrayList<Integer>) paths[prev_machine][task_index_on_machine].clone();
            }
        }

        lengths[m1][n1] = max + input_v[task_number][operation_index]; // dodaj do max długości poprzedzających wierzchołków, wartość własną
        path.add(m1 * n + n1); // dodaj numer operacji do najdłuższej ścieżki - tutaj numeruje zadania (tak jak w pracy)
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
