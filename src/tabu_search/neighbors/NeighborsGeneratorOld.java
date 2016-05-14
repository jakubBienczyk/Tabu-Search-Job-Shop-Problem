package tabu_search.neighbors;

import tabu_search.input.InputManager;
import java.util.ArrayList;
import java.util.Arrays;
import tabu_search.TabuList;

public class NeighborsGeneratorOld {

    private final int[][] pi;
    private final TabuList tabu;
    private final InputManager input;
    private final Integer[] pathList;
    private int[][] next;
    private int s, e;
    private int actual_f, actual_s, saved_f, saved_s;
    private boolean is_moved; 

    public NeighborsGeneratorOld(int[][] pi, TabuList tabu, InputManager input, ArrayList<Integer> pathList) {
        this.input = input;
        this.pi = pi;
        this.tabu = tabu;
        this.pathList = pathList.toArray(new Integer[pathList.size()]);
        next = null;
        s = e = -1;
        getNextBlock();
    }

    private void getNextBlock() {

        if (s >= 0 && e < 0) { // jeżeli tak jest, to już nie ma bloków
            return;
        }

        s = e = e + 1; // punkt początkowy

        int n = input.getN();
        int size = pathList.length;
        if (e > size) { // jeżeli wyszliśmy poza lisę to kończymy
            e = -1;
            return;
        }

        while ((e + 1) < size) { // idziemy na koniec listy
            if (pathList[s] / n == pathList[e + 1] / n) {
                e++;
            } else {
                break;
            }
        }

        if (e == s) { // jeżeli blok jest jednoelementowy to szukamy dalej
            getNextBlock();
        }

        is_moved = false; // jeszcze nie ruszliśmy tego bloku
    }

    public boolean hasNext() {

        int n = input.getN();

        if (e < 0) { // jeżeli e < 0 to już nie ma bloków do wykorzystania
            return false;
        }

        if (!is_moved) { // jeżeli jeszcze nie ruszaliśmy tego bloku to zamienamy pierwszy element bloku
            is_moved = true;
            if (s > 0) { // pod warunkiem, że nie jest to pierwszy blok
                next = pi.clone();
                for(int i = 0; i < pi.length; i++){
                    next[i] = pi[i].clone();
                }
                // zamieniamy element s z s+1
                int a = next[pathList[s + 1] / n][pathList[s + 1] % n];
                next[pathList[s + 1] / n][pathList[s + 1] % n] = next[pathList[s] / n][pathList[s] % n];
                next[pathList[s] / n][pathList[s] % n] = a;
                actual_f = s;
                actual_s = s + 1;
                return true;
            } else {
                return hasNext();
            }
        } else { // jeżeli ruszyliśmy już ten blok to zamienamy ostatnie elementy bloku
            if (e < pathList.length - 1) { // o ile nie jest to ostatni blok
                next = pi.clone();
                for(int i = 0; i < pi.length; i++){
                    next[i] = pi[i].clone();
                }
                // zamienamy e z e-1
                int a = next[pathList[e - 1] / n][pathList[e - 1] % n];
                int b = next[pathList[e] / n][pathList[e] % n];
                next[pathList[e - 1] / n][pathList[e - 1] % n] = b;
                next[pathList[e] / n][pathList[e] % n] = a;
                
                actual_f = e - 1;
                actual_s = e;
                
                getNextBlock(); //skończyliśmy już z tym blokiem - idziemy dalej
                return true;
            } else {
                return false;
            }
        }

    }

    public int[][] next() {
        return next;
    }
    
    public void saveFAndS() {
        saved_f = actual_f;
        saved_s = actual_s;
    }

    public int getActual_f() {
        return actual_f;
    }

    public int getActual_s() {
        return actual_s;
    }

    public int getSaved_f() {
        return saved_f;
    }

    public int getSaved_s() {
        return saved_s;
    }
    
    

}
