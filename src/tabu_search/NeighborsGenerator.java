package tabu_search;

import java.util.ArrayList;
import java.util.Arrays;

public class NeighborsGenerator {

    private final int[][] pi;
    private final TabuList tabu;
    private final InputManager input;
    private final Integer[] pathList;
    private int[][] next;
    private int s, e; //start and end of one block
    private int to_move; // element to move
    private boolean is_moved; // if already moved

    public NeighborsGenerator(int[][] pi, TabuList tabu, InputManager input, ArrayList<Integer> pathList) {
        this.input = input;
        this.pi = pi;
        this.tabu = tabu;
        this.pathList = pathList.toArray(new Integer[pathList.size()]);
        next = null;
        s = e = -1;
        getNextBlock();
    }

    private void getNextBlock() {
        System.out.println("e: " + e + " s: " + s);
        if (s >= 0 && e < 0) {
            return;
        }
        s = e = e + 1;
        int n = input.getN();
        int size = pathList.length;
        if (e > size) {
            e = -1;
            return;
        }

        while ((e + 1) < size) {
            if (pathList[s] / n == pathList[e + 1] / n) {
                e++;
            } else {
                break;
            }
        }

        if (e == s) {
            getNextBlock();
        }
        to_move = s;
        is_moved = false;
    }

    public boolean hasNext() {
        System.out.println("e: " + e + " s: " + s);
        int n = input.getN();
        if (e < 0) {
            return false;
        }

        if (!is_moved) {
            is_moved = true;
            if (to_move == s + 1) {
                next = pi.clone();
                int a = next[pathList[to_move] / n][pathList[to_move] % n];
                next[pathList[to_move] / n][pathList[to_move] % n] = next[pathList[s] / n][pathList[s] % n] ;
                next[pathList[s] / n][pathList[s] % n] = a;
                if(to_move == e){
                    getNextBlock();
                }
                return true;
            }
        } else {
            is_moved = false;
            if (to_move == e - 1) {
                next = pi.clone();
                int a = next[pathList[to_move] / n][pathList[to_move] % n];
                next[pathList[to_move] / n][pathList[to_move] % n] = next[pathList[e] / n][pathList[e] % n] ;
                next[pathList[e] / n][pathList[e] % n] = a;
                to_move++;
                return true;
            }
        }
        
        if(to_move != e - 1 && to_move != s + 1){
            if(to_move >= e)
                getNextBlock();
            else
                to_move++;
        }

        
        return hasNext();
    }

    public int[][] next() {
        return next;
    }

}
