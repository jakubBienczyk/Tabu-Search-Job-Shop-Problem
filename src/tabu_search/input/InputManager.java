package tabu_search.input;

import tabu_search.heuristic.StarterHeuristics;
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
                        case 4: known_upper_bound = v; break;
                        case 5: known_lower_bound = v; break;
                        //default: throw new RuntimeException("invalid input on first line");
                    }
                } else if (idx >= 2 && idx < 2 + n) { // 'Times' section
                    input_v[idx-2][j] = Integer.valueOf(item);
                } else if (idx >= 3 + n && idx < 3 + n + n) { // 'Machines' section
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
