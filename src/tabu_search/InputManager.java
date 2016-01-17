package tabu_search;

import java.nio.file.Files;
import java.nio.file.Path;

public class InputManager {
    
    // zakładam, że każde zadanie będzie przeprowadzone na każdej maszynie (modyfikacja tego założenia jest łatwa
    // wystarczy dać czas zerowe dla maszyn, których jednak nie chcemy
    // z plików będę pobierał dwie tablice, opisane poniżej
    // wynik (czyli graf) także będę przedstawiał na tablicy, ale w trochę "dziwny" sposób
    
    private int n, m; // n - liczba zadań, m -liczba maszyn
    private int[][] input_m; // [n][m] - dla zadania k i operacji l (tzn l-tej operacji w ciągu) input_m[k][l] wskazuje na mszynę,
                                                // na której ta operacja ma zostać wykonana
    private int[][] input_v; // [n][m] - dla zadania k i operacji l input_v wskazuje na czas wykonania tej operacji
    private int[][] starter; // [m][n] - początkowy wynik - jego generowanie powinno zostać przeniesione do innej klasy
                                    // wyniki przechowuje w tablicy m x n (czyli inaczej niż inputy!) 
                                    // tablica przedstawia kolejność wykonywania operacji dla danej maszyny
                                    // czyli dla maszyny k starter[k] (a później pi albo best_pi) wypisuje ciąg zadań wykonywanych kolejno na tej maszynie
    private int known_lower_bound;
    private int known_upper_bound;
    
    public InputManager(Path path) throws Exception {
        int idx = 0;
        input_m = new int[0][0]; // just so that Java doesn't complain that
        input_v = new int[0][0]; // it might be uninitialized
        n = 0;
        m = 0;
        
        for(String line : Files.readAllLines(path)) {
            int j = 0;
            for(String item : line.split("\\s+")) {
                int v = 0;
                try {
                    v = Integer.valueOf(item);
                } catch (NumberFormatException nfe) {
                    continue; // don't worry, it's just string 'Times' or 'Machines'
                }
                if(idx == 0) { // first line of input
                    switch(j) {
                        case 0: n = v; 
                        break;
                        case 1: m = v;
                                input_m = new int[n][m];
                                input_v = new int[n][m];
                                break;
                        case 2: known_upper_bound = v; break;
                        case 3: known_lower_bound = v; break;
                        default: throw new RuntimeException("invalid input on first line");
                    }
                } else if (idx >= 2 && idx < 2 + n) { // 'Times' section
                    input_v[idx-2][j] = Integer.valueOf(item);
                } else if (idx >= 3 + n && idx < 3 + n + m) { // 'Machines' section
                    input_m[idx-3-n][j] = Integer.valueOf(item);
                }
                j++;
            }
            idx++;
        }
        
        for (int i = 0; i < n; i++){ // numerowanie od zera
            for(int j = 0; j < m; j++){
                input_m[i][j] = input_m[i][j] -1 ;
            }
        }
        
        starter = (new StarterHeuristics(n, m, input_v, input_m)).get();
        
        /*
        //dostaje ścieżkę, z której pobiera dane
         n = 15; // liczba zadań
         m = 15; // liczba maszyn
        
        //zadania i maszyny numeruję od 0! w danych wejściowych jest od 1, więc będzie trzeba jakoś odjąć 1 od wszystkiego
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; // czyli zadanie 0  najpierw wykonuje operacje na maszynie 0, 
                                                                        // potem na maszynie 1, a na koniec na maszynie 2
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}}; // n x m
        int[][] starter = {{0, 1, 2, 3}, {3, 0, 1, 2}, {1, 2, 3, 0}};   // czyli maszyna 0 najpierw przyjmuje operację z zadania
                                                                        // 0, potem operację z zadania 1, itd. 
                                                                        // nigdzie nie numeruję samych operacji!
        
        int[][] input_v2 = {{94, 66, 10, 53, 26, 15, 65, 82, 10, 27, 93, 92, 96, 70, 83},
                            {74, 31, 88, 51, 57, 78, 8, 7, 91, 79, 18, 51, 18, 99, 33},
                            {4, 82, 40, 86, 50, 54, 21, 6, 54, 68, 82, 20, 39, 35, 68},
                            {73, 23, 30, 30, 53, 94, 58, 93, 32, 91, 30, 56, 27, 92, 9},
                            {78, 23, 21, 60, 36, 29, 95, 99, 79, 76, 93, 42, 52, 42, 96},
                            {29, 61, 88, 70, 16, 31, 65, 83, 78, 26, 50, 87, 62, 14, 30},
                            {18, 75, 20, 4, 91, 68, 19, 54, 85, 73, 43, 24, 37, 87, 66},
                            {32, 52, 9, 49, 61, 35, 99, 62, 6, 62, 7, 80, 3, 57, 7},
                            {85, 30, 96, 91, 13, 87, 82, 83, 78, 56, 85, 8, 66, 88, 15},
                            {5, 59, 30, 60, 41, 17, 66, 89, 78, 88, 69, 45, 82, 6, 13},
                            {90, 27, 1, 8, 91, 80, 89, 49, 32, 28, 90, 93, 6, 35, 73},
                            {47, 43, 75, 8, 51, 3, 84, 34, 28, 60, 69, 45, 67, 58, 87},
                            {65, 62, 97, 20, 31, 33, 33, 77, 50, 80, 48, 90, 75, 96, 44},
                            {28, 21, 51, 75, 17, 89, 59, 56, 63, 18, 17, 30, 16, 7, 35},
                            {57, 16, 42, 34, 37, 26, 68, 73, 5, 8, 12, 87, 83, 20, 97}};
        
        int[][] input_m2 = {{7, 13, 5, 8, 4, 3, 11, 12, 9, 15, 10, 14, 6, 1, 2},
                            {5, 6, 8, 15, 14, 9, 12, 10, 7, 11, 1, 4, 13, 2, 3},
                            {2, 9, 10, 13, 7, 12, 14, 6, 1, 3, 8, 11, 5, 4, 15},
                            {6, 3, 10, 7, 11, 1, 14, 5, 8, 15, 12, 9, 13, 2, 4},
                            {8, 9, 7, 11, 5, 10, 3, 15, 13, 6, 2, 14, 12, 1, 4},
                            {6, 4, 13, 14, 12, 5, 15, 8, 3, 2, 11, 1, 10, 7, 9},
                            {13, 4, 8, 9, 15, 7, 2, 12, 5, 6, 3, 11, 1, 14, 10},
                            {12, 6, 1, 8, 13, 14, 15, 2, 3, 9, 5, 4, 10, 7, 11},
                            {11, 12, 7, 15, 1, 2, 3, 6, 13, 5, 9, 8, 10, 14, 4},
                            {7, 12, 10, 3, 9, 1, 14, 4, 11, 8, 2, 13, 15, 5, 6},
                            {5, 8, 14, 1, 6, 13, 7, 9, 15, 11, 4, 2, 12, 10, 3},
                            {3, 15, 1, 13, 7, 11, 8, 6, 9, 10, 14, 2, 4, 12, 5},
                            {6, 9, 11, 3, 4, 7, 10, 1, 14, 5, 2, 12, 13, 8, 15},
                            {9, 15, 5, 14, 6, 7, 10, 2, 13, 8, 12, 11, 4, 3, 1},
                            {11, 9, 13, 7, 5, 2, 14, 15, 12, 1, 8, 4, 3, 10, 6}};
        for (int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                input_m2[i][j] = input_m2[i][j] -1 ;
            }
        }
        
        // przykładowy początek = najlepsza odpowiedz to 1828 (dla tabu długości 9)
        int[][] starter2 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14},
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
        
        // lepszy początek, wynik: 1231 - to najlepszy wynik
        int[][] starter22 = {{7, 11, 9, 10, 8, 3, 12, 2, 14, 5, 1, 6, 4, 0, 13},
                                {2, 8, 7, 14, 6, 13, 9, 11, 4, 5, 12, 3, 10, 1, 0},
                                {11, 9, 3, 0, 4, 12, 8, 7, 5, 2, 6, 14, 13, 10, 1},
                                {6, 5, 0, 9, 12, 7, 11, 10, 14, 1, 13, 2, 3, 4, 8},
                                {10, 13, 0, 4, 1, 5, 14, 3, 7, 6, 12, 8, 2, 9, 11},
                                {5, 7, 3, 12, 13, 10, 1, 11, 8, 4, 2, 6, 0, 9, 14},
                                {9, 0, 4, 8, 3, 11, 13, 14, 6, 12, 10, 2, 1, 7, 5},
                                {4, 6, 7, 10, 0, 11, 1, 9, 3, 5, 13, 14, 8, 2, 12},
                                {13, 4, 6, 2, 9, 14, 12, 11, 7, 10, 0, 1, 3, 8, 5},
                                {9, 3, 2, 4, 13, 11, 12, 1, 0, 7, 8, 5, 14, 10, 6},
                                {8, 14, 4, 3, 11, 12, 9, 0, 10, 13, 5, 1, 6, 2, 7},
                                {7, 9, 8, 5, 6, 0, 2, 3, 1, 13, 14, 12, 4, 10, 11},
                                {6, 0, 7, 11, 5, 14, 10, 2, 4, 13, 8, 9, 3, 12, 1},
                                {13, 10, 7, 9, 5, 3, 11, 1, 12, 14, 2, 4, 0, 6, 8},
                                {13, 11, 6, 8, 7, 4, 1, 5, 3, 10, 0, 14, 9, 2, 12}};
        
        this.input_m = input_m2;
        this.input_v = input_v2;
        this.starter = starter2; 
        */
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
    
    public int getKnownLB() {
        return known_lower_bound;
    }
    
    public int getKnownUB() {
        return known_upper_bound;
    }

}
