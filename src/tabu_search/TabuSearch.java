package tabu_search;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class TabuSearch {

    private int[][] best_pi; // [m][n] - graf (najpierw początkowy, później najlepszy)
    private final InputManager input;
    private final int loopsNumber;

    public TabuSearch(Path path, int loopsNumber) throws Exception {
        input = new InputManager(path); // pobieranie danych
        best_pi = input.getStarter(); // graf początkowy - tutaj trzeba wstawić jakiś algorytm (konstrukcyjny, albo naiwny)
        this.loopsNumber = loopsNumber; // liczba pętli do wykonania
    }

    public void run() {

        setLongestPath(); // ustawienie statycznych zmiennych do liczenia najdłuższej ścieżki

        LongestPath longestPath = new LongestPath(best_pi); // znajdź ścieżkę dla początkowego grafu
        int max = longestPath.calculate(); // długość tej ścieżki
        //System.out.println("Max path = " + max);
        ArrayList<Integer> pathList = longestPath.getLongest_path(); // ścieżka

        int[][] pi = best_pi.clone(); // pi będzie grafem początkowym dla każdej iteracji, best_pi - najlepszym rozwiązaniem
        TabuList tabuList = new TabuList(); // używana lista tabu

        for (int loop = 0; loop < loopsNumber; loop++) {
            //System.out.println("Loop: " + loop);

            NeighborsGenerator generator = new NeighborsGenerator(pi, null, input, pathList); // wygeneruj otoczenie dla grafu pi

            int neigh_max = -1; // długość najkrótszej najdłuższej ścieżki dla grafów z otoczenia 
            while (generator.hasNext()) { // czy są jeszcze jakieś grafy w otoczeniu
                int[][] next = generator.next(); //pobierz graf z otoczenia
                //System.out.println("Next: " + Arrays.deepToString(next));
                longestPath = new LongestPath(next); // policz najdłuższą scieżkę
                int max_neigh_path = longestPath.calculate(); // tutaj jej wartość
                //System.out.println("Path = " + max_neigh_path);
                if (neigh_max < 0 || max_neigh_path <= neigh_max) { // jeżeli jest to pierwszy lub najlepszy graf z otoczenia to zapisz go
                    if(tabuList.contains(generator.getActual_f(), generator.getActual_s()) &&  max_neigh_path >= max) { // chyba, że jesteśmy w tabu list
                        continue;
                    }
                    neigh_max = max_neigh_path;
                    pi = next.clone(); // graf iteracyjny zmienia się w najlepszy graf z otoczenia, więc możemy go zmieniać już tutaj
                    pathList = longestPath.getLongest_path(); // tak samo jak ścieżkę tego grafu
                    generator.saveFAndS(); // zapisz indeksy zmian, które chcemy zrobić
                }
            }
            
            if (neigh_max < 0) { // jeżeli otoczenie jest puste to możemy kończyć algorytm
                break;
            }
            
            tabuList.add(generator.getSaved_f(), generator.getSaved_s());

            if (neigh_max < max) { // jeżeli trafiliśmy na lepsze rozwiązanie niż mieliśmy do tej pory to zapiszmy je
                max = neigh_max;
                best_pi = pi.clone();
            }
            //System.out.println("Max path: " + max); //wypisz aktualnie najlepsze rozwiązanie
        }

        // wypisz rozwiązanie: 
        System.out.println("max = " + max); 
        System.out.println("ans: " + Arrays.deepToString(best_pi));

    }

    private void setLongestPath() { // ustawienie statycznych zmiennych do liczenia najdłuższej ścieżki
        LongestPath.setInput_m(input.getInput_m());
        LongestPath.setInput_v(input.getInput_v());
        LongestPath.setM(input.getM());
        LongestPath.setN(input.getN());
    }
}
