package tabu_search;

import java.util.ArrayList;

public class TabuList {

    private final ArrayList<Pair> tabuList;
    private final int max_size;

    public TabuList() {
        this(10);
    }
    
    public TabuList(int size) {
        max_size = size;
        tabuList = new ArrayList<>();
    }

    public void add(int f, int s) {
        tabuList.add(new Pair(f, s));
        if (tabuList.size() > max_size) {
            tabuList.remove(0);
        }
    }

    public boolean contains(int f, int s) {
        return tabuList.contains(new Pair(f, s));
    }
    
    public int position(int f, int s){
        return tabuList.indexOf(new Pair(f, s));
    }
    
    public void remove(int elements){
        for(int i = 0; i < elements; i++){
            tabuList.remove(0);
        }
    }
    
    public void clear() {
        tabuList.clear();
    }

    private class Pair {

        private final int f, s;

        public Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        public int getF() {
            return f;
        }

        public int getS() {
            return s;
        }

        public boolean equals(Pair obj) {
            return (obj.getF() == f) && (obj.getS() == s);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                return equals((Pair) obj);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "(" + f + ", " + s + ")"; 
        }

    }

}
