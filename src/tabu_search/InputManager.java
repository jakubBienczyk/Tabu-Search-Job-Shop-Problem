package tabu_search;

public class InputManager {

    private final int n, m; // n - liczba zadań, m -liczba maszyn
    private final int[][] input_m; // [n][m] - maszyny odpowiadające kolejnym operacjom, dla każdego zadania
    private final int[][] input_v; // [n][m] - czas wykoniania każdej operacji
    private final int[][] starter; // [m][n] - początkowe pi (wynik)

    public InputManager(String path) {
        //dostaje ścieżkę, z której pobiera dane
        n = 4; // liczba zadań
        m = 3; // liczba maszyn
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; //n x m 
        this.input_m = input_m;
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}}; //n x m
        this.input_v = input_v;
        int[][] starter = {{0, 1, 2, 3}, {3, 0, 1, 2}, {1, 2, 3, 0}};   //m x n
        this.starter = starter;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int[][] getInput_m() {
        return input_m;
    }

    public int[][] getInput_v() {
        return input_v;
    }

    public int[][] getStarter() {
        return starter;
    }

}
