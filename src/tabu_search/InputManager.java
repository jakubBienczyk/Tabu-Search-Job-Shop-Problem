package tabu_search;

public class InputManager {
    
    // zakładam, że każde zadanie będzie przeprowadzone na każdej maszynie (modyfikacja tego założenia jest łatwa
    // wystarczy dać czas zerowe dla maszyn, których jednak nie chcemy
    // z plików będę pobierał dwie tablice, opisane poniżej
    // wynik (czyli graf) także będę przedstawiał na tablicy, ale w trochę "dziwny" sposób
    
    private final int n, m; // n - liczba zadań, m -liczba maszyn
    private final int[][] input_m; // [n][m] - dla zadania k i operacji l (tzn l-tej operacji w ciągu) input_m[k][l] wskazuje na mszynę,
                                                // na której ta operacja ma zostać wykonana
    private final int[][] input_v; // [n][m] - dla zadania k i operacji l input_v wskazuje na czas wykonania tej operacji
    private final int[][] starter; // [m][n] - początkowy wynik - jego generowanie powinno zostać przeniesione do innej klasy
                                    // wyniki przechowuje w tablicy m x n (czyli inaczej niż inputy!) 
                                    // tablica przedstawia kolejność wykonywania operacji dla danej maszyny
                                    // czyli dla maszyny k starter[k] (a później pi albo best_pi) wypisuje ciąg zadań wykonywanych kolejno na tej maszynie

    public InputManager(String path) {
        //dostaje ścieżkę, z której pobiera dane
        n = 4; // liczba zadań
        m = 3; // liczba maszyn
        
        //zadania i maszyny numeruję od 0! w danych wejściowych jest od 1, więc będzie trzeba jakoś odjąć 1 od wszystkiego
        int[][] input_m = {{0, 1, 2}, {2, 1, 0}, {0, 2, 1}, {1, 0, 2}}; // czyli zadanie 0  najpierw wykonuje operacje na maszynie 0, 
                                                                        // potem na maszynie 1, a na koniec na maszynie 2
        this.input_m = input_m;
        int[][] input_v = {{4, 7, 8}, {5, 8, 6}, {7, 9, 3}, {8, 2, 7}}; // n x m
        this.input_v = input_v;
        int[][] starter = {{0, 1, 2, 3}, {3, 0, 1, 2}, {1, 2, 3, 0}};   // czyli maszyna 0 najpierw przyjmuje operację z zadania
                                                                        // 0, potem operację z zadania 1, itd. 
                                                                        // nigdzie nie numeruję samych operacji!
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
